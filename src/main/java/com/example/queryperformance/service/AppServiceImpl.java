package com.example.queryperformance.service;

import com.example.queryperformance.domain.DataSourceConnectionProvider;
import com.example.queryperformance.dto.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.System.nanoTime;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private PropertyScanner propertyScanner;

    private static final long SLEEP_TIME = 2000;
    private static final String SQL_FIND_BY_NAME = "SELECT * FROM USER WHERE `name` = ? ";

    @Override
    public List<ResultDto> benchmark() {
        List<Thread> threads = new ArrayList<>();
        List<ResultDto> result = new ArrayList<>();
        List<DataSourceConnectionProvider> dataSourceConnectionProvider = propertyScanner.getDataSourcesFromProps();

        for (DataSourceConnectionProvider provider : dataSourceConnectionProvider) {
            Thread thread = new Thread(() -> {
                try (final Connection connection = provider.getConnection();
                     PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_NAME)) {
                    final long startTime = nanoTime();
                    statement.setString(1, "mike");
                    statement.executeQuery();
                    Thread.sleep(SLEEP_TIME);
                    final Double resultTime = (double) (nanoTime() - startTime) / 1_000_000_000;
                    result.add(new ResultDto(provider.getUrl(), resultTime, Thread.currentThread().getName()));
                } catch (SQLException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
        }
        threads.parallelStream().forEach(thread -> {
            thread.start();
        });
        return result;
    }

    public List<ResultDto> method() throws ExecutionException, InterruptedException {
        List<Callable<ResultDto>> tasks = new ArrayList<>();
        List<ResultDto> benchmarkResult = new ArrayList<>();
        List<DataSourceConnectionProvider> dataSourceConnectionProvider = propertyScanner.getDataSourcesFromProps();
        ExecutorService executorService = Executors.newFixedThreadPool(dataSourceConnectionProvider.size());

        for (DataSourceConnectionProvider provider : dataSourceConnectionProvider) {
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
        for (Callable<ResultDto> task : tasks) {
            Future<ResultDto> result = executorService.submit(task);
            benchmarkResult.add(result.get());
        }
        executorService.shutdown();
        return benchmarkResult;
    }
}
