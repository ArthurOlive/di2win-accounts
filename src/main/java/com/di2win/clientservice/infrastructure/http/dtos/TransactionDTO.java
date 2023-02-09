package com.di2win.clientservice.infrastructure.http.dtos;

import com.di2win.clientservice.domain.transaction.TransationType;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransactionDTO {

    @NotNull
    private TransationType type;
    @NotNull
    @Digits(integer = 10, fraction = 2, message = "value is in invalid format")
    @DecimalMin(value = "0.01", message = "value is less than minimum")
    private BigDecimal value;
    @NotNull
    private long agency;
    @NotNull
    private long account;

}
