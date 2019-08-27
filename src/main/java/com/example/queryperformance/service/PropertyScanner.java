package com.example.queryperformance.service;

import com.example.queryperformance.domain.DataSourceConnectionProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyScanner {

    private final Environment environment;

    public List<DataSourceConnectionProvider> getDataSourcesFromProps() throws AppException {
        List<DataSourceConnectionProvider> providers = new ArrayList<>();
        for (int i = 0; ; i++) {
            String username = environment.getProperty("datasource" + i + ".username");
            String password = environment.getProperty("datasource" + i + ".password");
            String url = environment.getProperty("datasource" + i + ".url");

            if (username == null || password == null || url == null) {
                if (providers.size() == 0)
                    throw new AppException("No props found");
                return providers;
            }
            providers.add(new DataSourceConnectionProvider(username, password, url));
        }
    }
}
