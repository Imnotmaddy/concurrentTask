package com.example.queryperformance.controller;

import com.example.queryperformance.service.AppException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CustomExceptionController {

    @ExceptionHandler(AppException.class)
    public String acceptIt(AppException ex) {
        return ex.getMessage();
    }

}
