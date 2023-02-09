package com.di2win.clientservice.infrastructure.http.routers;

import com.di2win.clientservice.application.usecases.account.BlockAccountUseCase;
import com.di2win.clientservice.application.usecases.account.ConsultAccountUseCase;
import com.di2win.clientservice.application.usecases.account.CreateAccountUseCase;
import com.di2win.clientservice.application.usecases.account.DeleteAccountUseCase;
import com.di2win.clientservice.infrastructure.http.dtos.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/account")
@RestController
public class AccountController {

    @Autowired
    private CreateAccountUseCase createAccountUseCase;

    @Autowired
    private ConsultAccountUseCase consultAccountUseCase;

    @Autowired
    private BlockAccountUseCase blockAccountUseCase;

    @Autowired
    private DeleteAccountUseCase deleteAccountUseCase;

    @PostMapping
    public ResponseEntity<?> post(@RequestBody @Valid AccountDTO accountDTO) throws Exception {
        return ResponseEntity.ok(createAccountUseCase.handle(accountDTO));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> getByCpf(@PathVariable("cpf") String cpf) throws Exception {
        return ResponseEntity.ok(consultAccountUseCase.handle(cpf));
    }

    @PutMapping("/{cpf}/toogleBlock")
    public ResponseEntity<?> toggleStatusAccount (@PathVariable("cpf") String cpf) throws Exception {
        return ResponseEntity.ok(blockAccountUseCase.handle(cpf));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deleteAccount (@PathVariable("cpf") String cpf) throws Exception {
        return ResponseEntity.ok(deleteAccountUseCase.handle(cpf));
    }
}
