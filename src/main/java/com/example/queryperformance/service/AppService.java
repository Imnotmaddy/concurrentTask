package com.example.queryperformance.service;

import com.example.queryperformance.dto.ResultDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AppService {
    List<ResultDto> benchmark() throws InterruptedException, ExecutionException, AppException;

}
