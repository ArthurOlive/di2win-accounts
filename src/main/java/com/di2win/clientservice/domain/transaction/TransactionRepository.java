package com.di2win.clientservice.domain.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t FROM Transaction t WHERE t.createAt >=:init AND t.createAt <=:end AND " +
            "t.account.agency =:agency AND t.account.number =:account")
    List<Transaction> getByPeriod(
            @Param("init") LocalDateTime init,
            @Param("end") LocalDateTime end,
            @Param("agency") long agency,
            @Param("account") long account
    );

}
