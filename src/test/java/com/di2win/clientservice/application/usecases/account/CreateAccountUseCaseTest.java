package com.di2win.clientservice.application.usecases.account;

import com.di2win.clientservice.domain.account.Account;
import com.di2win.clientservice.domain.account.AccountRepository;
import com.di2win.clientservice.infrastructure.http.dtos.AccountDTO;
import com.di2win.clientservice.infrastructure.http.dtos.HttpResponseDTO;
import com.di2win.clientservice.infrastructure.http.handlerException.RequestCustomException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CreateAccountUseCaseTest {

    @InjectMocks
    private CreateAccountUseCase createAccountUseCase;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validCpf() throws Exception {
        String validCpf = "61487161000";

        Account account = new Account();
        account.setCpf(validCpf);
        account.setNome("Nome qualquer");
        account.setDataNascimento(LocalDate.of(2000, 3, 1));

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setCpf(validCpf);
        accountDTO.setNome("Nome qualquer");
        accountDTO.setDataNascimento(LocalDate.of(2000, 3, 1));

        when(accountRepository.getByCpf(validCpf)).thenReturn(Optional.empty());
        when(accountRepository.save(Mockito.any())).thenReturn(account);

        assertEquals(
                account,
                createAccountUseCase.handle(accountDTO).getContent()
        );
    }

    @Test
    public void invalidCpf() throws Exception {
        String validCpf = "11122233344";

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setCpf(validCpf);
        accountDTO.setNome("Nome qualquer");
        accountDTO.setDataNascimento(LocalDate.of(2000, 3, 1));

        Exception e = assertThrows(RequestCustomException.class, () -> {
                    createAccountUseCase.handle(accountDTO).getContent();
                }
        );

        assertEquals("Invalid Cpf", e.getMessage());
    }

    @Test
    public void alreadyExistsAccountWithCpf() throws Exception {
        String validCpf = "61487161000";

        Account account = new Account();
        account.setCpf(validCpf);
        account.setNome("Nome qualquer");
        account.setDataNascimento(LocalDate.of(2000, 3, 1));

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setCpf(validCpf);
        accountDTO.setNome("Nome qualquer");
        accountDTO.setDataNascimento(LocalDate.of(2000, 3, 1));

        when(accountRepository.getByCpf(validCpf)).thenReturn(Optional.of(account));

        Exception e = assertThrows(RequestCustomException.class, () -> {
                    createAccountUseCase.handle(accountDTO).getContent();
                }
        );

        assertEquals("Cpf has register in system", e.getMessage());
    }

    @Test
    public void cannotSaveAccountException() throws Exception {
        String validCpf = "61487161000";

        Account account = new Account();
        account.setCpf(validCpf);
        account.setNome("Nome qualquer");
        account.setDataNascimento(LocalDate.of(2000, 3, 1));

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setCpf(validCpf);
        accountDTO.setNome("Nome qualquer");
        accountDTO.setDataNascimento(LocalDate.of(2000, 3, 1));

        when(accountRepository.getByCpf(validCpf)).thenReturn(Optional.empty());
        doThrow(new RuntimeException()).when(accountRepository).save(Mockito.any());

        Exception e = assertThrows(RequestCustomException.class, () -> {
                    createAccountUseCase.handle(accountDTO).getContent();
                }
        );

        assertEquals("Account cannot be created!", e.getMessage());
    }
}
