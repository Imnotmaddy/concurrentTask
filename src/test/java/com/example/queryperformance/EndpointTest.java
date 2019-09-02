package com.example.queryperformance;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {QueryPerformanceApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EndpointTest {
    @Autowired
    MockMvc mockMvc;

    private final static String BENCHMARK = "/benchmark";
    private final static String DATA_SOURCE = "/dataSource";
    private final static String DATA_SOURCE_PARAM = "dataSources";
    private final static String QUERY_PARAM = "query";
    private final static Integer DATA_SOURCE_QUANTITY = 3;

    @Test
    public void contextLoads(){

    }

    @Test
    public void whenEmptyDataSource_thenAppException() throws Exception {
        mockMvc.perform(post(BENCHMARK).requestAttr(QUERY_PARAM, "select * from users")
                .requestAttr(DATA_SOURCE_PARAM, ""))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenNotExistingDataSource_thenAppException() throws Exception {
        mockMvc.perform(post(BENCHMARK).requestAttr(QUERY_PARAM, "select * from users")
                .requestAttr(DATA_SOURCE_PARAM, "IDontExistDataSource"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenEmptyQuery_thenBadRequest() throws Exception {
        mockMvc.perform(post(BENCHMARK).requestAttr(QUERY_PARAM, "")
                .requestAttr(QUERY_PARAM, ""))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetDataSourcesEndpoint() throws Exception {
        mockMvc.perform(get(DATA_SOURCE)).andExpect(jsonPath("$", hasSize(DATA_SOURCE_QUANTITY)));
    }

}
