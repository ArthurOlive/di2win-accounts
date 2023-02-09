package com.di2win.clientservice.domain.account;

import com.di2win.clientservice.domain.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Account {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    private BigDecimal saldo = BigDecimal.ZERO;

    @Generated(GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "number_generator")
    @SequenceGenerator(name="number_generator", sequenceName="number_seq", allocationSize=1)
    private long number;

    @Column(nullable = false)
    private long agency;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    private Boolean isBlock = false;

    private LocalDateTime createAt = LocalDateTime.now();

}
