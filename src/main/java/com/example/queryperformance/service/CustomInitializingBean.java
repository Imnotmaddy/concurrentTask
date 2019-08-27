package com.example.queryperformance.service;

import com.example.queryperformance.domain.DataSourceConnectionProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomInitializingBean implements InitializingBean {

    private final PropertyScanner propertyScanner;

    private final static String CREATE_TABLE = "CREATE TABLE if not exists user  (id INTEGER not NULL, " +
            " name VARCHAR(255), " +
            " PRIMARY KEY ( id ))";
    private final static String SQL_INSERT_ATTACHMENT = "REPLACE INTO user (id,name) VALUES (?,?) ";

    @Override
    public void afterPropertiesSet() throws Exception {
        final List<DataSourceConnectionProvider> sources = propertyScanner.getDataSourcesFromProps();
        for (DataSourceConnectionProvider source : sources) {
            final Connection connection = source.getConnection();
            try (final Statement statement = connection.createStatement()) {
                statement.execute(CREATE_TABLE);
            }
            try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ATTACHMENT)) {
                statement.setInt(1, 1);
                statement.setString(2, "mike");
                statement.executeUpdate();
            }
            connection.close();
        }
    }
}
