package com.example.queryperformance.service;

import com.example.queryperformance.model.DataSourceConnectionProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyScanner {

    private final Environment environment;

    @Value("${benchmark.usedDataSources}")
    private List<String> dataSources;



    public List<DataSourceConnectionProvider> getDataSourcesFromProps() throws AppException {
        List<DataSourceConnectionProvider> providers = new ArrayList<>();
        for (String source : dataSources) {
            String username = environment.getProperty(source + ".username");
            String password = environment.getProperty(source + ".password");
            String url = environment.getProperty(source + ".url");

            if (username == null || password == null || url == null) {
                if (providers.size() == 0)
                    throw new AppException("No props found");
                return providers;
            }
            providers.add(new DataSourceConnectionProvider(username, password, url));
        }
        return providers;
    }
}
