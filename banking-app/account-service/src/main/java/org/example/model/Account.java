package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    private Long accountNumber;
    private String accountHolderName;
    private String emailId;
    private String userName;
    private String password;
    private BigDecimal balance;
    private String phoneNumber;
    private Boolean active;

}
