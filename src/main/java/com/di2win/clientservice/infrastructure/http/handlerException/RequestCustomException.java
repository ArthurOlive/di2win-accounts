package com.di2win.clientservice.infrastructure.http.handlerException;

public class RequestCustomException extends Exception {

    public RequestCustomException (String str) {
        super(str);
    }
}
