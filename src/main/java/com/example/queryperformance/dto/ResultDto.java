package com.example.queryperformance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultDto {
    private String testedDbUrl;
    private Double time;
    private String executedThreadName;
}
