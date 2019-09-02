package com.example.queryperformance.service;

import com.example.queryperformance.exception.AppException;
import com.example.queryperformance.model.DataSourceConnectionProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

@Service
public class CustomInitializingBean implements InitializingBean {

    private final PropertyScanner propertyScanner;

    private final static String SOURCE_FILE_PATH = "schema.sql";

    public CustomInitializingBean(PropertyScanner propertyScanner) {
        this.propertyScanner = propertyScanner;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final List<DataSourceConnectionProvider> sources = propertyScanner.getDataSourcesFromProps();
        for (DataSourceConnectionProvider source : sources) {
            final Connection connection = source.getConnection();
            try (final Statement statement = connection.createStatement()) {
                statement.execute("RUNSCRIPT FROM '" + getSourceDataFilePath() + "'");
            }
            connection.close();
        }
    }

    private String getSourceDataFilePath() throws AppException {
        final URL resource = CustomInitializingBean.class.getClassLoader().getResource(SOURCE_FILE_PATH);
        if (resource == null) throw new AppException("Missing source scripts for DB instantiation");
        return resource.getPath();
    }
}
