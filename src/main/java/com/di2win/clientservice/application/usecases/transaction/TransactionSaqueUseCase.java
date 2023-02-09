package com.di2win.clientservice.application.usecases.transaction;

import com.di2win.clientservice.application.usecases.account.CreateAccountUseCase;
import com.di2win.clientservice.domain.account.Account;
import com.di2win.clientservice.domain.account.AccountRepository;
import com.di2win.clientservice.domain.transaction.Transaction;
import com.di2win.clientservice.domain.transaction.TransactionRepository;
import com.di2win.clientservice.domain.transaction.TransationType;
import com.di2win.clientservice.infrastructure.http.dtos.HttpResponseDTO;
import com.di2win.clientservice.infrastructure.http.dtos.TransactionDTO;
import com.di2win.clientservice.infrastructure.http.handlerException.RequestCustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class TransactionSaqueUseCase {

    private final Logger logger = LoggerFactory.getLogger(TransactionSaqueUseCase.class);

    @Value("${props.LIMITE_DIARIO}")
    private long LIMITEDIARIO = 2000;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public HttpResponseDTO<String> handle(TransactionDTO transactionDTO) throws Exception {

        if (transactionDTO.getType() != TransationType.SAQUE) {
            throw new RequestCustomException("Type is invalid to this operation!");
        }

        //Limite diario de 2000 reais conforme descrito nos requisitos
        if (transactionDTO.getValue().compareTo( BigDecimal.valueOf(LIMITEDIARIO) ) > 0) {
            throw new RequestCustomException("Diary limit exceeded");
        }

        Optional<Account> query = accountRepository.getByAgencyAndNumber(transactionDTO.getAgency(), transactionDTO.getAccount());

        if (query.isEmpty()) {
            throw new RequestCustomException("Agency or Account is invalid");
        }

        Account account = query.get();

        if (account.getIsBlock()){
            throw new RequestCustomException("Account is blocked!");
        }

        if (account.getSaldo().subtract( transactionDTO.getValue() ).compareTo(BigDecimal.ZERO) < 0){
            throw new RequestCustomException("Value in account is insufficient to realize the operation");
        }

        try {
            Transaction transaction = new Transaction();

            transaction.setValue(transactionDTO.getValue());
            transaction.setType(transactionDTO.getType());
            transaction.setAccount(account);

            account.setSaldo(account.getSaldo().subtract(transactionDTO.getValue()));
            account.getTransactions().add(transaction);

            accountRepository.save(account);

            return new HttpResponseDTO<>("Withdraw realized with successful!");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new RequestCustomException("Unable to make withdraw money");
        }
    }
}
