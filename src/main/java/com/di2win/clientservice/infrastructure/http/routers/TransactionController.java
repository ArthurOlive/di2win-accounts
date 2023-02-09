package com.di2win.clientservice.infrastructure.http.routers;

import com.di2win.clientservice.application.usecases.transaction.TransactionDepositeUseCase;
import com.di2win.clientservice.application.usecases.transaction.TransactionReportUseCase;
import com.di2win.clientservice.application.usecases.transaction.TransactionSaqueUseCase;
import com.di2win.clientservice.infrastructure.http.dtos.ReportDTO;
import com.di2win.clientservice.infrastructure.http.dtos.TransactionDTO;
import com.di2win.clientservice.infrastructure.http.handlerException.RequestCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/transaction")
@RestController
public class TransactionController {

    @Autowired
    private TransactionDepositeUseCase transactionDepositeUseCase;

    @Autowired
    private TransactionSaqueUseCase transactionSaqueUseCase;

    @Autowired
    private TransactionReportUseCase transactionReportUseCase;

    @PostMapping("/deposito")
    public ResponseEntity<?> deposito(@RequestBody @Valid TransactionDTO transactionDTO) throws Exception {
        return ResponseEntity.ok(transactionDepositeUseCase.handle(transactionDTO));
    }

    @PostMapping("/saque")
    public ResponseEntity<?> saque(@RequestBody @Valid TransactionDTO transactionDTO) throws Exception {
        return ResponseEntity.ok(transactionSaqueUseCase.handle(transactionDTO));
    }

    @PostMapping("/report")
    public ResponseEntity<?> report(@RequestBody @Valid ReportDTO reportDTO) throws Exception {
        return ResponseEntity.ok(transactionReportUseCase.handle(reportDTO));
    }
}
