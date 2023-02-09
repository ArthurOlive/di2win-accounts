package com.di2win.clientservice.application.usecases.transaction;

import com.di2win.clientservice.domain.account.Account;
import com.di2win.clientservice.domain.account.AccountRepository;
import com.di2win.clientservice.domain.transaction.Transaction;
import com.di2win.clientservice.domain.transaction.TransactionRepository;
import com.di2win.clientservice.domain.transaction.TransationType;
import com.di2win.clientservice.infrastructure.http.dtos.HttpResponseDTO;
import com.di2win.clientservice.infrastructure.http.dtos.TransactionDTO;
import com.di2win.clientservice.infrastructure.http.handlerException.RequestCustomException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class TransactionDepositeUseCase {

    private final Logger logger = LoggerFactory.getLogger(TransactionDepositeUseCase.class);
    ;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public HttpResponseDTO<String> handle(TransactionDTO transactionDTO) throws Exception {

        if (transactionDTO.getType() != TransationType.DEPOSITO) {
            throw new RequestCustomException("Type is invalid to this operation!");
        }

        Optional<Account> queryAccount = accountRepository.getByAgencyAndNumber(transactionDTO.getAgency(), transactionDTO.getAccount());

        if (queryAccount.isEmpty()) {
            throw new RequestCustomException("Agency or Account is invalid");
        }

        Account account = queryAccount.get();

        if (account.getIsBlock()){
            throw new RequestCustomException("Account is blocked!");
        }

        try {
            Transaction transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setType(transactionDTO.getType());
            transaction.setValue(transactionDTO.getValue());

            account.setSaldo(account.getSaldo().add(transactionDTO.getValue()));
            account.getTransactions().add(transaction);

            accountRepository.save(account);

            return new HttpResponseDTO<>("Deposit made successfully!");
        } catch (Exception e) {
            logger.warn(e.toString());
            throw new RequestCustomException("Cannot delete account!");
        }
    }
}
