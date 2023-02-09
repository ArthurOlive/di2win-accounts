package com.di2win.clientservice.infrastructure.http.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HttpResponseDTO <T> {

    private T content;
    private LocalDateTime time = LocalDateTime.now();

    public HttpResponseDTO(T content) {
        this.content = content;
    }

}
