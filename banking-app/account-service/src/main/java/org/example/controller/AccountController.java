package org.example.controller;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AccountRequest;
import org.example.dto.AccountResponse;
import org.example.model.Account;
import org.example.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

   private final AccountService accountService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> createAccount(@RequestBody AccountRequest accountRequest,
                              @RequestHeader("x-auth-token") String authToken ){
        try{

            accountService.createAccount(authToken, accountRequest);
            log.info("Created an account:");
            return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
        } catch(Exception e)
        {
            return new ResponseEntity<>("Invalid token", HttpStatus.FORBIDDEN);
        }
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
