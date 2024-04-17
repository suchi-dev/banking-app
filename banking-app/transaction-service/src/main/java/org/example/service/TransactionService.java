package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.model.Transaction;
import org.example.repository.TransactionRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WebClient.Builder webClientBuilder;



    public void createTransaction(TransactionRequest transactionRequest){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Transaction transaction = Transaction.builder()
                .fromAccountNumber(transactionRequest.getFromAccountNumber())
                .toAccountNumber(transactionRequest.getToAccountNumber())
                .transactionAmount(transactionRequest.getTransactionAmount())
                .transactionDate(timestamp).build();

        // Call account service for validating balance on sendAccount,
        //      and presence of toAccount

        WebClient webClientAccount = webClientBuilder.baseUrl("http://account-service").build();

        AccountResponse accountResponse = webClientAccount.get().uri(
                uriBuilder ->
            uriBuilder.path("/api/accounts/{accountNumber}")
                            .build(transaction.getFromAccountNumber()))
                        .retrieve()
                                .bodyToMono(AccountResponse.class)
                                        .block();

        System.out.println("The balance is : "+ accountResponse.getBalance());

        if(accountResponse.getBalance().compareTo(transaction.getTransactionAmount()) > 0.0){
            transactionRepository.save(transaction);
        }else {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Publish notification to notification service

        WebClient webClientNotification = webClientBuilder.baseUrl("http://notification-service").build();
        HashMap<String, String> reqMap = new HashMap<>();
        reqMap.put("accountHolderName", accountResponse.getAccountHolderName());
        reqMap.put("amount", ""+transaction.getTransactionAmount());
        reqMap.put("fromAccountNumber", ""+transaction.getFromAccountNumber());
        reqMap.put("toAccountNumber", ""+transaction.getToAccountNumber());


        NotificationRequest notificationRequest = NotificationRequest.builder()
                        .locale("EN")
                                .templateName("balance_transfer")
                                        .emailId(accountResponse.getEmailId())
                                                .accountNumber(transaction.getFromAccountNumber())
                                                        .fields(reqMap).build();


        NotificationResponse notificationResponse = webClientNotification.post().uri(
                "/api/notifications")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(notificationRequest), NotificationRequest.class)
                .retrieve()
                .bodyToMono(NotificationResponse.class)
                .block();
        System.out.println("Notification response : "+ notificationResponse.getResolvedContent());







    }
    public List<TransactionResponse> getTransactionHistory(Long accountNumber){
        List<Transaction> fromTransactions = transactionRepository.findByFromAccountNumber(accountNumber);
        List<Transaction> toTransactions = transactionRepository.findByToAccountNumber(accountNumber);
        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(fromTransactions);
        allTransactions.addAll(toTransactions);
        return allTransactions.stream()
                .map(this:: mapToTxnResponse)
                .toList();
    }


    public TransactionResponse mapToTxnResponse(Transaction transaction){
        return  TransactionResponse.builder()
                .fromAccountNumber(transaction.getFromAccountNumber())
                .toAccountNumber(transaction.getToAccountNumber())
                .transactionAmount(transaction.getTransactionAmount())
                .transactionId(transaction.getTransactionId())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }

    public List<TransactionResponse> getAllTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(this:: mapToTxnResponse)
                .toList();

    }

    public TransactionResponse getTransactionById(Long transactionId){
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        return mapToTxnResponse(transaction);

    }

}
