package com.example.queryperformance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {
    private String dataSourceName;
    private Double time;
    private String executedThreadName;
    private String sqlException = "NONE";

    public ResultDto(String dataSourceName, Double time, String executedThreadName) {
        this.dataSourceName = dataSourceName;
        this.time = time;
        this.executedThreadName = executedThreadName;
    }
}
