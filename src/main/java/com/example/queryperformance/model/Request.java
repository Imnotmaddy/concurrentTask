package com.example.queryperformance.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@NoArgsConstructor
@Setter
public class Request {
    private String query;
    private Set<String> dataSources;
}
