package com.di2win.clientservice.application.usecases.account;

import com.di2win.clientservice.application.utils.DocumentValidator;
import com.di2win.clientservice.domain.account.Account;
import com.di2win.clientservice.domain.account.AccountRepository;
import com.di2win.clientservice.infrastructure.http.dtos.HttpResponseDTO;
import com.di2win.clientservice.infrastructure.http.handlerException.RequestCustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ConsultAccountUseCase {

    private final Logger logger = LoggerFactory.getLogger(ConsultAccountUseCase.class);;

    @Autowired
    private AccountRepository accountRepository;

    public HttpResponseDTO<List<Account>> handle() {
        return new HttpResponseDTO<>(accountRepository.findAll());
    }

    public HttpResponseDTO<Optional<Account>> handle(String cpf) throws Exception {
        if (cpf == null) throw new RequestCustomException("Invalid Cpf");
        return new HttpResponseDTO<>(accountRepository.getByCpf(cpf));
    }
}
