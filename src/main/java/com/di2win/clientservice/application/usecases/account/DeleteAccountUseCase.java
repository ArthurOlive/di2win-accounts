package com.di2win.clientservice.application.usecases.account;

import com.di2win.clientservice.domain.account.Account;
import com.di2win.clientservice.domain.account.AccountRepository;
import com.di2win.clientservice.infrastructure.http.dtos.HttpResponseDTO;
import com.di2win.clientservice.infrastructure.http.handlerException.RequestCustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteAccountUseCase {
    private final Logger logger = LoggerFactory.getLogger(ConsultAccountUseCase.class);;

    @Autowired
    private AccountRepository accountRepository;

    public HttpResponseDTO<String> handle (String cpf) throws Exception {

        if (cpf == null) throw new RequestCustomException("Invalid Cpf");

        Optional<Account> queryAccount = accountRepository.getByCpf(cpf);

        if (queryAccount.isEmpty()) {
            throw new RequestCustomException("Account not exists!");
        }

        Account account = queryAccount.get();

        try {
            accountRepository.delete(account);
            return new HttpResponseDTO<>("Account is deleted with successful");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new RequestCustomException("Cannot delete account!");
        }

    }
}
