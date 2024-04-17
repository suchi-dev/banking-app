package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AccountRequest;
import org.example.dto.AccountResponse;
import org.example.model.Account;
import org.example.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{accountNumber}")
    @ResponseBody
    public AccountResponse getByAccountNo(@PathVariable Long accountNumber){
        return accountService.getAccountResponseByNumber(accountNumber);
    }

}
