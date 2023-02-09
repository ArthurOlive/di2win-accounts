package com.di2win.clientservice.infrastructure.http.handlerException;

import com.di2win.clientservice.infrastructure.http.dtos.HttpResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<HttpResponseDTO<Map<String, String>>> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(
                new HttpResponseDTO<>(errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ResponseEntity<HttpResponseDTO<String>> handleHttpMessageNotReadableException(
            WebRequest request,
            HttpMessageNotReadableException ex
    ) {
        String cause = "";
        if (ex.getCause() instanceof JsonProcessingException) {
            final JsonProcessingException jpe = (JsonProcessingException) ex.getCause();
            cause = jpe.getOriginalMessage();
        } else {
            cause = ex.getLocalizedMessage();
        }

        return new ResponseEntity<>(
                new HttpResponseDTO<>(cause),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({RequestCustomException.class})
    public ResponseEntity<HttpResponseDTO<String>> handleHttpMessageNotReadableException(
            WebRequest request,
            RequestCustomException ex
    ) {
        return new ResponseEntity<>(
                new HttpResponseDTO<>(ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

}

