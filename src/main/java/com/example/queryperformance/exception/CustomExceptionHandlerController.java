package com.example.queryperformance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomExceptionHandlerController {

    @ExceptionHandler(AppException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String acceptIt(AppException ex) {
        return ex.getMessage();
    }

}
