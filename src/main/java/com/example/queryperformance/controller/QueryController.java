package com.example.queryperformance.controller;

import com.example.queryperformance.dto.ResultDto;
import com.example.queryperformance.service.AppException;
import com.example.queryperformance.service.AppService;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class QueryController {

    @Autowired
    private AppService appService;

    @PostMapping("/benchmark")
    @Synchronized
    public List<ResultDto> benchmark() throws ExecutionException, InterruptedException, AppException {
        return appService.benchmark();
    }

    @ExceptionHandler(AppException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public String handleAppErrors(AppException ex) {
        return ex.getMessage();
    }
}

