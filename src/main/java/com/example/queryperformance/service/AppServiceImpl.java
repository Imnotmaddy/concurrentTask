package com.example.queryperformance.service;

import com.example.queryperformance.dto.ResultDto;
import com.example.queryperformance.model.DataSourceConnectionProvider;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static java.lang.System.nanoTime;

@Service
public class AppServiceImpl implements AppService {

    private final PropertyScanner propertyScanner;

    private static final long SLEEP_TIME = 4000;

    public AppServiceImpl(PropertyScanner propertyScanner) {
        this.propertyScanner = propertyScanner;
    }


    @Override
    @Synchronized
    public List<ResultDto> benchmark(String query, Set<String> dataSources) throws ExecutionException, InterruptedException, AppException {
        List<ResultDto> benchmarkResult = new ArrayList<>();
        List<DataSourceConnectionProvider> dataSourceConnectionProvider = propertyScanner.getDataSourcesFromProps();
        List<Callable<ResultDto>> tasks = createTasks(dataSourceConnectionProvider, query, dataSources);
        ExecutorService executorService = Executors.newFixedThreadPool(dataSources.size());

        List<Future<ResultDto>> futures = new ArrayList<>(executorService.invokeAll(tasks));
        for (Future<ResultDto> future : futures) {
            benchmarkResult.add(future.get());
        }
        executorService.shutdown();
        return benchmarkResult;
    }

    private List<Callable<ResultDto>> createTasks(List<DataSourceConnectionProvider> providers, String query, Set<String> dataSources) {
        List<Callable<ResultDto>> tasks = new ArrayList<>();
        providers.stream().filter(obj -> dataSources.contains(obj.getName())).forEach(provider -> {
            Callable<ResultDto> task = () -> {
                ResultDto result = new ResultDto(provider.getName(), null, Thread.currentThread().getName());
                try (final Connection connection = provider.getConnection();
                     PreparedStatement statement = connection.prepareStatement(query)) {
                    final long startTime = nanoTime();
                    statement.execute();
                    Thread.sleep(SLEEP_TIME);
                    result.setTime((double) (nanoTime() - startTime) / 1_000_000_000);
                } catch (SQLException | InterruptedException e) {
                    result.setSqlException(e.getMessage());
                }
                return result;
            };
            tasks.add(task);
        });
        return tasks;
    }

    public Set<String> getDataSources() {
        return propertyScanner.getDataSourceNames();
    }
}
