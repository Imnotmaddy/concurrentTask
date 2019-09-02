package com.example.queryperformance;

import com.example.queryperformance.model.DataSourceConnectionProvider;
import com.example.queryperformance.service.AppException;
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

}
