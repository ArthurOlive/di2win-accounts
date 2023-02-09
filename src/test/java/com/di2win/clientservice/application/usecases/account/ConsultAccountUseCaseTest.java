package com.di2win.clientservice.application.usecases.account;

import com.di2win.clientservice.domain.account.Account;
import com.di2win.clientservice.domain.account.AccountRepository;
import com.di2win.clientservice.infrastructure.http.handlerException.RequestCustomException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultAccountUseCaseTest {

    @InjectMocks
    private ConsultAccountUseCase consultAccountUseCase;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void containsAccount() throws Exception {
        String validCpf = "61487161000";

        Account account = new Account();
        account.setCpf(validCpf);
        account.setNome("Nome qualquer");
        account.setDataNascimento(LocalDate.of(2000, 3, 1));

        when(accountRepository.getByCpf(Mockito.anyString())).thenReturn(Optional.of(account));

        assertEquals(account, consultAccountUseCase.handle(validCpf).getContent().get());
    }

    @Test
    public void notContainsAccount() throws Exception {
        String validCpf = "61487161000";

        when(accountRepository.getByCpf(Mockito.anyString())).thenReturn(Optional.empty());

        assertEquals(null, consultAccountUseCase.handle(validCpf).getContent().orElse(null));
    }

}
