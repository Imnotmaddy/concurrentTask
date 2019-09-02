package com.example.queryperformance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {
    private String testedDbUrl;
    private Double time;
    private String executedThreadName;
    private String sqlException = "NONE";

    public ResultDto(String testedDbUrl, Double time, String executedThreadName) {
        this.testedDbUrl = testedDbUrl;
        this.time = time;
        this.executedThreadName = executedThreadName;
    }
}
