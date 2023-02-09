package com.di2win.clientservice.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> getByCpf(String cpf);

    @Query("SELECT a FROM Account a WHERE a.number =:account AND a.agency =:agency")
    Optional<Account> getByAgencyAndNumber(@Param("agency") long agency, @Param("account") long account);

}
