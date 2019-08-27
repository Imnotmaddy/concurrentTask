package com.example.queryperformance.service;

import com.example.queryperformance.domain.DataSourceConnectionProvider;
import com.example.queryperformance.dto.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.System.nanoTime;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private PropertyScanner propertyScanner;

    private static final long SLEEP_TIME = 5000;
    private static final String SQL_FIND_BY_NAME = "SELECT * FROM USER WHERE `name` = ? ";

    public List<ResultDto> benchmark() throws ExecutionException, InterruptedException, AppException {
        List<ResultDto> benchmarkResult = new ArrayList<>();
        List<DataSourceConnectionProvider> dataSourceConnectionProvider = propertyScanner.getDataSourcesFromProps();
        List<Callable<ResultDto>> tasks = createTasks(dataSourceConnectionProvider);
        ExecutorService executorService = Executors.newFixedThreadPool(dataSourceConnectionProvider.size());

        List<Future<ResultDto>> futures = new ArrayList<>(executorService.invokeAll(tasks));
        for (Future<ResultDto> future : futures) {
            benchmarkResult.add(future.get());
        }
        executorService.shutdown();
        return benchmarkResult;
    }

    private List<Callable<ResultDto>> createTasks(List<DataSourceConnectionProvider> providers) {
        List<Callable<ResultDto>> tasks = new ArrayList<>();
        for (DataSourceConnectionProvider provider : providers) {
            Callable<ResultDto> task = () -> {
                ResultDto result = new ResultDto(provider.getUrl(), null, Thread.currentThread().getName());
                try (final Connection connection = provider.getConnection();
                     PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_NAME)) {
                    final long startTime = nanoTime();
                    statement.setString(1, "mike");
                    statement.executeQuery();
                    Thread.sleep(SLEEP_TIME);
                    result.setTime((double) (nanoTime() - startTime) / 1_000_000_000);
                } catch (SQLException | InterruptedException e) {
                    e.printStackTrace();
                }
                return result;
            };
            tasks.add(task);
        }
        return tasks;
    }
}
