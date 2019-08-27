package com.example.queryperformance.controller;

import com.example.queryperformance.dto.ResultDto;
import com.example.queryperformance.service.AppService;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class QueryController {

    @Autowired
    private AppService appService;

    @PostMapping("/benchmark")
    @Synchronized
    public List<ResultDto> benchmark() throws ExecutionException, InterruptedException {
        return appService.benchmark();
    }
}
