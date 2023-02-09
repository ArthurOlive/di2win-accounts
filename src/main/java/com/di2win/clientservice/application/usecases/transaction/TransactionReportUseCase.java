package com.di2win.clientservice.application.usecases.transaction;

import com.di2win.clientservice.domain.account.Account;
import com.di2win.clientservice.domain.account.AccountRepository;
import com.di2win.clientservice.domain.transaction.Transaction;
import com.di2win.clientservice.domain.transaction.TransactionRepository;
import com.di2win.clientservice.infrastructure.http.dtos.HttpResponseDTO;
import com.di2win.clientservice.infrastructure.http.dtos.ReportDTO;
import com.di2win.clientservice.infrastructure.http.dtos.TransactionDTO;
import com.di2win.clientservice.infrastructure.http.handlerException.RequestCustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class TransactionReportUseCase {

    private final Logger logger = LoggerFactory.getLogger(TransactionReportUseCase.class);;


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public HttpResponseDTO<List<Transaction>> handle(ReportDTO transactionDTO) throws Exception {

        Optional<Account> query = accountRepository.getByAgencyAndNumber(transactionDTO.getAgency(), transactionDTO.getAccount());

        if (query.isEmpty()) {
            throw new RequestCustomException("Agency or Account is invalid");
        }

        Account account = query.get();

        if (account.getIsBlock()){
            throw new RequestCustomException("Account is blocked!");
        }

        try {
            List<Transaction> transactions = transactionRepository.getByPeriod(
                    LocalDateTime.of(transactionDTO.getInit(), LocalTime.now()),
                    LocalDateTime.of(transactionDTO.getEnd(), LocalTime.now()),
                    transactionDTO.getAgency(),
                    transactionDTO.getAccount()
            );

            return new HttpResponseDTO<>(transactions);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new RequestCustomException("Unable to make report");
        }
    }

}
