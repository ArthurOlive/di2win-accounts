package com.di2win.clientservice.application.usecases.account;

import com.di2win.clientservice.domain.account.Account;
import com.di2win.clientservice.domain.account.AccountRepository;
import com.di2win.clientservice.infrastructure.http.dtos.HttpResponseDTO;
import com.di2win.clientservice.infrastructure.http.handlerException.RequestCustomException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BlockAccountUseCase {
    private final Logger logger = LoggerFactory.getLogger(ConsultAccountUseCase.class);;

    @Autowired
    private AccountRepository accountRepository;

    public HttpResponseDTO<String> handle(String cpf) throws Exception {

        if (cpf == null) throw new RequestCustomException("Invalid Cpf");

        Optional<Account> queryAccount = accountRepository.getByCpf(cpf);

        if (queryAccount.isEmpty()) {
            throw new RequestCustomException("Account not exists!");
        }

        Account account = queryAccount.get();

        try {
            account.setIsBlock(!account.getIsBlock());
            accountRepository.save(account);

            return new HttpResponseDTO<>("Account status changed successfully!");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new RequestCustomException("Unable to change account status!");
        }
    }
}
