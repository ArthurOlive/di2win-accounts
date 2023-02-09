package com.di2win.clientservice.application.usecases.account;

import com.di2win.clientservice.domain.account.Account;
import com.di2win.clientservice.domain.account.AccountRepository;
import com.di2win.clientservice.infrastructure.http.dtos.AccountDTO;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAccountUseCaseTest {

    @InjectMocks
    private DeleteAccountUseCase deleteAccountUseCase;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deletedWithSuccess() throws Exception {

        String validCpf = "61487161000";

        Account account = new Account();
        account.setCpf(validCpf);
        account.setNome("Nome qualquer");
        account.setDataNascimento(LocalDate.of(2000, 3, 1));

        when(accountRepository.getByCpf(validCpf)).thenReturn(Optional.of(account));
        doAnswer((i) -> null).when(accountRepository).delete(Mockito.any());

        assertEquals(
                "Account is deleted with successful",
                deleteAccountUseCase.handle(validCpf).getContent()
        );
    }

    @Test
    public void cannotDeleteAccount() throws Exception {

        String validCpf = "61487161000";

        Account account = new Account();
        account.setCpf(validCpf);
        account.setNome("Nome qualquer");
        account.setDataNascimento(LocalDate.of(2000, 3, 1));

        when(accountRepository.getByCpf(validCpf)).thenReturn(Optional.of(account));
        doThrow(new RuntimeException()).when(accountRepository).delete(Mockito.any());

        Exception e = assertThrows(RequestCustomException.class, () -> {
                    deleteAccountUseCase.handle(validCpf).getContent();
                }
        );

        assertEquals(
                "Cannot delete account!",
                e.getMessage()
        );
    }

    @Test
    public void accountNotExists() throws Exception {
        String validCpf = "61487161000";

        when(accountRepository.getByCpf(validCpf)).thenReturn(Optional.empty());

        Exception e = assertThrows(RequestCustomException.class, () -> {
                    deleteAccountUseCase.handle(validCpf).getContent();
                }
        );

        assertEquals("Account not exists!", e.getMessage());
    }

    @Test
    public void invalidCpf() throws Exception {
        String validCpf = "11122233344";

        Exception e = assertThrows(RequestCustomException.class, () -> {
                    deleteAccountUseCase.handle(validCpf).getContent();
                }
        );

        assertEquals("Account not exists!", e.getMessage());
    }

}
