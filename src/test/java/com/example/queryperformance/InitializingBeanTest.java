package com.example.queryperformance;

import com.example.queryperformance.service.PropertyScanner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class InitializingBeanTest {
    @Autowired
    PropertyScanner propertyScanner;

}
