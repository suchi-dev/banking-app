package org.example.service;


import org.example.dto.AccountRequest;
import org.example.dto.AccountResponse;
import org.example.repository.AccountRepository;
import org.example.model.Account;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {


    private static Account account1;
    private static AccountRequest accountRequest;
    private static final String accountHolderName = "Suchitra";
    private static final Long accountNumber = 100000234L;
    private static final String emailId = "suchitra@gmail.com";
    private static final String username = "suchitra";
    private static final String password = "password1";
    private static final String phoneNumber = "9002003003";
    private static List<Account> accountList = new ArrayList<>();

    @Mock
    private AccountRepository accountRepositoryMock;

    @InjectMocks
    private AccountService accountServiceMock;

    @BeforeAll
    public static void setUp() throws Exception {
        account1 = Account.builder()
                        .accountHolderName(accountHolderName)
                                .password(password)
                                        .accountNumber(accountNumber)
                                                .emailId(emailId)
                                                        .phoneNumber(phoneNumber)
                                                                .userName(username)
                                                                        .balance(new BigDecimal(10000000.00))
                                                                                .build();


        Account account2 = Account.builder()
                .accountHolderName("Srinivas")
                .accountNumber(200030300L)
                .balance(new BigDecimal(50000000.00))
                .userName("srini")
                .emailId("srini@gmail.com")
                .phoneNumber("988818299")
                .password("testing1")
                .build();
        accountList.add(account1);
        accountList.add(account2);
        accountRequest = AccountRequest.builder()
                        .accountHolderName(accountHolderName)
                        .password(password)
                        .emailId(emailId)
                        .phoneNumber(phoneNumber)
                        .accountNumber(accountNumber)
                        .userName(username)
                        .build();


    }

@Test
public void createAccount(){


        AccountRequest accountReq = AccountRequest.builder()
                .accountHolderName(account1.getAccountHolderName())
                .accountNumber(accountNumber)
                .emailId(account1.getEmailId())
                .phoneNumber(account1.getPhoneNumber())
                .password(account1.getPassword())
                .userName(account1.getUserName()).build();

    Mockito.when(accountRepositoryMock.save(ArgumentMatchers.any(Account.class)))
            .thenReturn(account1);
    accountServiceMock.createAccount(accountRequest);
    Mockito.verify(accountRepositoryMock,times(1)).save(ArgumentMatchers.any(Account.class));
    }


    @Test
    public void testForAllAccounts() {
        Account account1 = Account.builder()
                .accountHolderName("Sita M")
                .accountNumber(10010111110l)
                .userName("sitam")
                .password("Testing1")
                .phoneNumber("9899102002")
                .emailId("sitam@gmail.com")
                .active(true)
                .balance(new BigDecimal(100000.00)).build();
        Account account2 = Account.builder()
                .accountHolderName("Shyam Sagar")
                .accountNumber(1002020033l)
                .userName("shyamsag")
                .password("Testing2")
                .phoneNumber("9828738838")
                .emailId("shyamsag@gmail.com")
                .active(true)
                .balance(new BigDecimal(1200000.00)).build();
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        Mockito.when(accountRepositoryMock.findAll()).thenReturn(accounts);

        List<AccountResponse> actualResponse = accountServiceMock.getAccountResponseList();
        assertEquals(2,actualResponse.size());
        assertTrue(actualResponse.get(1).getActive());

    }

    public List<AccountResponse> getAccountResponseList(){
        List<Account> accountList = accountRepositoryMock.findAll();

        return accountList.stream()
                .map(this::mapToAccountResponse)
                .toList();
    }

    @Test
    public void getAccountResponseByNumberTest(){

        Mockito.when(accountRepositoryMock.findByAccountNumber(accountNumber))
               .thenReturn(account1);

       AccountResponse actualResponse = accountServiceMock.getAccountResponseByNumber(accountNumber);
      assertEquals("Suchitra", actualResponse.getAccountHolderName() );
    }

    @Test
    public void testWithDrawAmount(){
        Mockito.when(accountRepositoryMock.findByAccountNumber(accountNumber))
                .thenReturn(account1);
        Mockito.when(accountRepositoryMock.save(ArgumentMatchers.any(Account.class)))
                .thenReturn(account1);
        AccountResponse accountResponse = accountServiceMock.withdrawAmt(accountNumber, new BigDecimal(200000.00));
        assertEquals(new BigDecimal(9800000), accountResponse.getBalance());

    }

    @Test
    public void testDepositAmount(){
        Mockito.when(accountRepositoryMock.findByAccountNumber(accountNumber))
                .thenReturn(account1);
        Mockito.when(accountRepositoryMock.save(ArgumentMatchers.any(Account.class)))
                .thenReturn(account1);
        AccountResponse accountResponse = accountServiceMock.depositAmt(accountNumber, new BigDecimal(500000.00));
        assertEquals(new BigDecimal(10300000), accountResponse.getBalance());

    }

    @Test
    public void testBalanceEnquiry() {
        Mockito.when(accountRepositoryMock.findByAccountNumber(accountNumber))
                .thenReturn(account1);
        BigDecimal balance = accountServiceMock.getAccountBalance(accountNumber);
        assertEquals(new BigDecimal(10000000), balance);

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
