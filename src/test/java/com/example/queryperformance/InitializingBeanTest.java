package com.example.queryperformance;

import com.example.queryperformance.domain.DataSourceConnectionProvider;
import com.example.queryperformance.service.PropertyScanner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class InitializingBeanTest {
    @Autowired
    PropertyScanner propertyScanner;

    @Test
    public void onStartupTablesAreCreatedForEachDataSourceProp() throws SQLException {
        final List<DataSourceConnectionProvider> dataSourcesFromProps = propertyScanner.getDataSourcesFromProps();

        assertThat(dataSourcesFromProps.size()).isEqualTo(3);

        for (DataSourceConnectionProvider provider : dataSourcesFromProps) {
            final Connection connection = provider.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user");
            assertThat(statement.executeQuery()).isNotNull();
        }
    }

}
