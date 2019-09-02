package com.example.queryperformance.service;

import com.example.queryperformance.exception.AppException;
import com.example.queryperformance.model.DataSourceConnectionProvider;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PropertyScanner {

    private final Environment environment;

    public Set<String> dataSourceNames = new HashSet<>();

    private final static String DATA_SOURCE = "datasource";

    public PropertyScanner(Environment environment) {
        this.environment = environment;
    }

    public List<DataSourceConnectionProvider> getDataSourcesFromProps() throws AppException {
        List<DataSourceConnectionProvider> providers = new ArrayList<>();
        for (int i = 0; ; i++) {
            final String dataSourceName = DATA_SOURCE + i;
            String username = environment.getProperty(dataSourceName + ".username");
            String password = environment.getProperty(dataSourceName + ".password");
            String url = environment.getProperty(dataSourceName + ".url");

            if (username == null || password == null || url == null) {
                if (providers.size() == 0)
                    throw new AppException("No props found");
                return providers;
            }
            providers.add(new DataSourceConnectionProvider(username, password, url, dataSourceName));
            dataSourceNames.add(dataSourceName);
        }
    }

    public Set<String> getDataSourceNames() {
        return dataSourceNames;
    }
}
