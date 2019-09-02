package com.example.queryperformance.controller;

import com.example.queryperformance.dto.ResultDto;
import com.example.queryperformance.exception.AppException;
import com.example.queryperformance.exception.CustomExceptionHandlerController;
import com.example.queryperformance.model.Request;
import com.example.queryperformance.service.AppService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
public class QueryController extends CustomExceptionHandlerController {

    private final AppService appService;

    public QueryController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping("/benchmark")
    @ResponseBody
    public List<ResultDto> benchmark(@Valid Request request) throws ExecutionException, InterruptedException, AppException {
        if (request.getDataSources().isEmpty())
            throw new AppException("Bad Request. Specify dataSources");
        return appService.benchmark(request.getQuery(), request.getDataSources());
    }

    @GetMapping("/dataSource")
    public Set<String> getDataSources() {
        return appService.getDataSources();
    }
}

