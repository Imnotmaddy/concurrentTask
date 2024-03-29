package com.example.queryperformance.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
@Getter
public class DataSourceConnectionProvider {
    private final String username;
    private final String password;
    private final String url;
    private final String name;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
