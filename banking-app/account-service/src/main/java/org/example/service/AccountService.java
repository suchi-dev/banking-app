package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AccountRequest;
import org.example.dto.AccountResponse;
import org.example.repository.AccountRepository;
import org.example.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void createAccount(AccountRequest accountRequest){
        Random random = new Random();
        long accountNumber = random.nextLong(1000000000000L);

        Account account = Account.builder()
                .accountHolderName(accountRequest.getAccountHolderName())
                .accountNumber(accountNumber)
                .emailId(accountRequest.getEmailId())
                .phoneNumber(accountRequest.getPhoneNumber())
                .password(accountRequest.getPassword())
                .userName(accountRequest.getUserName()).build();

        accountRepository.save(account);
    }

    public List<AccountResponse> getAccountResponseList(){
        List<Account> accountList = accountRepository.findAll();

        return accountList.stream()
                .map(this::mapToAccountResponse)
                .toList();
    }

    public AccountResponse getAccountResponseByNumber(Long accountNumber){
        Account account = accountRepository.findByAccountNumber(accountNumber);
        return mapToAccountResponse(account);
    }

    private AccountResponse mapToAccountResponse(Account account){
        return AccountResponse.builder()
                .accountHolderName(account.getAccountHolderName())
                .accountNumber(account.getAccountNumber())
                .userName(account.getUserName())
                .password(account.getPassword())
                .phoneNumber(account.getPhoneNumber())
                .emailId(account.getEmailId())
                .active(account.getActive())
                .balance(account.getBalance()).build();
        }

}
