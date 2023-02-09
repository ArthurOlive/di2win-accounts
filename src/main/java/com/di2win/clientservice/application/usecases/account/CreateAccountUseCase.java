package com.di2win.clientservice.application.usecases.account;

import com.di2win.clientservice.application.usecases.transaction.TransactionDepositeUseCase;
import com.di2win.clientservice.application.utils.DocumentValidator;
import com.di2win.clientservice.domain.account.Account;
import com.di2win.clientservice.domain.account.AccountRepository;
import com.di2win.clientservice.infrastructure.http.dtos.AccountDTO;
import com.di2win.clientservice.infrastructure.http.dtos.HttpResponseDTO;
import com.di2win.clientservice.infrastructure.http.handlerException.RequestCustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountUseCase {

    private final Logger logger = LoggerFactory.getLogger(CreateAccountUseCase.class);;

    @Autowired
    private AccountRepository accountRepository;

    public HttpResponseDTO<Account> handle(AccountDTO accountDTO) throws Exception {
        boolean isCpf = DocumentValidator.isCPF(accountDTO.getCpf());

        if (!isCpf) {
            throw new RequestCustomException("Invalid Cpf");
        }

        boolean containsCpf = accountRepository.getByCpf(accountDTO.getCpf()).isPresent();

        if (containsCpf) {
            throw new RequestCustomException("Cpf has register in system");
        }

        try {
            Account account = new Account();
            account.setCpf(accountDTO.getCpf());
            account.setNome(accountDTO.getNome());
            account.setDataNascimento(accountDTO.getDataNascimento());

            account.setAgency(1);

            account = accountRepository.save(account);

            return new HttpResponseDTO<>(account);
        } catch (Exception e) {
            logger.warn(e.toString());
            throw new RequestCustomException("Account cannot be created!");
        }
    }
}
