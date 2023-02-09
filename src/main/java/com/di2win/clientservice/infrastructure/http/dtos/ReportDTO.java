package com.di2win.clientservice.infrastructure.http.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportDTO {
    private LocalDate init;
    private LocalDate end;
    private long agency;
    private long account;
}
