package com.di2win.clientservice.application.usecases.transaction;

import com.di2win.clientservice.domain.account.Account;
import com.di2win.clientservice.domain.account.AccountRepository;
import com.di2win.clientservice.domain.transaction.TransactionRepository;
import com.di2win.clientservice.domain.transaction.TransationType;
import com.di2win.clientservice.infrastructure.http.dtos.AccountDTO;
import com.di2win.clientservice.infrastructure.http.dtos.TransactionDTO;
import com.di2win.clientservice.infrastructure.http.handlerException.RequestCustomException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionDepositeUseCaseTest {

    @InjectMocks
    private TransactionDepositeUseCase transactionDepositeUseCase;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void transactionSuccessfulWithSaldo200AndSaque200() throws Exception {
        String validCpf = "61487161000";

        Account account = new Account();
        account.setCpf(validCpf);
        account.setNome("Nome qualquer");
        account.setSaldo(BigDecimal.valueOf(200));
        account.setIsBlock(false);
        account.setDataNascimento(LocalDate.of(2000, 3, 1));

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount(1);
        transactionDTO.setAgency(1);
        transactionDTO.setType(TransationType.DEPOSITO);
        transactionDTO.setValue(BigDecimal.valueOf(2000));

        when(accountRepository.getByAgencyAndNumber(1,1)).thenReturn(Optional.of(account));
        when(accountRepository.save(Mockito.any())).thenReturn(account);

        assertEquals(
                "Deposit made successfully!",
                transactionDepositeUseCase.handle(transactionDTO).getContent()
        );
    }

    @Test
    public void operationInvalid() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount(1);
        transactionDTO.setAgency(1);
        transactionDTO.setType(TransationType.SAQUE);
        transactionDTO.setValue(BigDecimal.valueOf(200));

        Exception e = assertThrows(RequestCustomException.class, () -> {
                    transactionDepositeUseCase.handle(transactionDTO).getContent();
                }
        );

        assertEquals(
                "Type is invalid to this operation!",
                e.getMessage()
        );
    }

    @Test
    public void blockedAccount() {

        String validCpf = "61487161000";

        Account account = new Account();
        account.setCpf(validCpf);
        account.setNome("Nome qualquer");
        account.setIsBlock(true);
        account.setDataNascimento(LocalDate.of(2000, 3, 1));

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount(1);
        transactionDTO.setAgency(1);
        transactionDTO.setType(TransationType.DEPOSITO);
        transactionDTO.setValue(BigDecimal.valueOf(200));

        when(accountRepository.getByAgencyAndNumber(1,1)).thenReturn(Optional.of(account));

        Exception e = assertThrows(RequestCustomException.class, () -> {
                    transactionDepositeUseCase.handle(transactionDTO).getContent();
                }
        );

        assertEquals(
                "Account is blocked!",
                e.getMessage()
        );
    }

    @Test
    public void accountOrAgencyIsInvalid() {

        String validCpf = "61487161000";

        Account account = new Account();
        account.setCpf(validCpf);
        account.setNome("Nome qualquer");
        account.setIsBlock(true);
        account.setDataNascimento(LocalDate.of(2000, 3, 1));

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount(1);
        transactionDTO.setAgency(1);
        transactionDTO.setType(TransationType.DEPOSITO);
        transactionDTO.setValue(BigDecimal.valueOf(200));

        when(accountRepository.getByAgencyAndNumber(1,1)).thenReturn(Optional.empty());

        Exception e = assertThrows(RequestCustomException.class, () -> {
                    transactionDepositeUseCase.handle(transactionDTO).getContent();
                }
        );

        assertEquals(
                "Agency or Account is invalid",
                e.getMessage()
        );
    }

}
