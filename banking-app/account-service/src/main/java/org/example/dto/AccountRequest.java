package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {
    private Long accountNumber;
    private String accountHolderName;
    private String emailId;
    private String userName;
    private String password;
    private String phoneNumber;
    private BigDecimal balance;
}
