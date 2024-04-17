package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.TransactionRequest;
import org.example.dto.TransactionResponse;
import org.example.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

   private final TransactionService txnService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody TransactionRequest txnRequest){
        txnService.createTransaction(txnRequest);
    }

    @GetMapping
    @ResponseBody
    public List<TransactionResponse> getAllTransactions(){
        return txnService.getAllTransactions();
    }

    @GetMapping("/{txnId}")
    @ResponseBody
    public TransactionResponse getByTxnId(@PathVariable Long txnId){
        return txnService.getTransactionById(txnId);
    }

    @GetMapping("/history/{accountNo}")
    @ResponseBody
    public List<TransactionResponse> getTxnHistory(@PathVariable Long accountNo){
        return txnService.getTransactionHistory(accountNo);
    }

}
