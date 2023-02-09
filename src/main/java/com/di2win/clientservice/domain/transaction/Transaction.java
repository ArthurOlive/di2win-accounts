package com.di2win.clientservice.domain.transaction;

import com.di2win.clientservice.domain.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Transaction {

    @Id
    private UUID id = UUID.randomUUID();

    private BigDecimal value = BigDecimal.ZERO;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private TransationType type;

    private LocalDateTime createAt = LocalDateTime.now();
}
