package com.example.queryperformance.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@NoArgsConstructor
@Setter
public class Request {
    @NotBlank
    private String query;
    @NotEmpty
    private Set<String> dataSources;
}
