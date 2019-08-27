package com.example.queryperformance;

import com.example.queryperformance.service.PropertyScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class QueryPerformanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryPerformanceApplication.class, args);
    }
}
