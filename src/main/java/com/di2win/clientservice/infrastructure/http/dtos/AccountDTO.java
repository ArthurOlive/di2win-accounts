package com.di2win.clientservice.infrastructure.http.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class AccountDTO {

    @NotBlank(message = "'nome' is required!")
    private String nome;
    @NotBlank(message = "'cpf' is required!")
    @Pattern(regexp = "\\d{11}$", message = "'cpf' is in invalid format. Expected numbers in format XXXXXXXXXXX")
    private String cpf;
    @NotNull(message = "'dataNascimento' is required!")
    @Past(message = "'dataNascimento' is in future")
    private LocalDate dataNascimento;

}
