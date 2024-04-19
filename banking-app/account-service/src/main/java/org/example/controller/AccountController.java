package org.example.controller;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.example.dto.AccountRequest;
import org.example.dto.AccountResponse;
import org.example.model.Account;
import org.example.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

   private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody AccountRequest accountRequest){
        accountService.createAccount(accountRequest);
    }

    @GetMapping
    @ResponseBody
    public List<AccountResponse> getAllAccounts(){
        return accountService.getAccountResponseList();
    }



    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountResponse getByAccountNo(@PathVariable Long accountNumber){
        return accountService.getAccountResponseByNumber(accountNumber);
    }

    @PostMapping("/withdraw/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountResponse withdrawAmt(@PathVariable Long accountNumber,
                                       @RequestParam BigDecimal withdrawAmt){
        return accountService.withdrawAmt(accountNumber, withdrawAmt);
    }
    @PostMapping("/deposit/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountResponse depositAmt(@PathVariable Long accountNumber,
                                       @RequestParam BigDecimal depositAmt){
        return accountService.depositAmt(accountNumber, depositAmt);
    }

    @GetMapping("/balance/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getBalance(@PathVariable Long accountNumber){
        return accountService.getAccountBalance(accountNumber);
    }

}
