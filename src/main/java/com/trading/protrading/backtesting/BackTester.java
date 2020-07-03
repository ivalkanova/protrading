package com.trading.protrading.backtesting;

import com.trading.protrading.model.Strategy;
import com.trading.protrading.repository.ReportRepository;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

public class BackTester {
    private ArrayBlockingQueue<StrategyTestObject> pastDataTestingStrategies;

    public synchronized void enableStrategy(Strategy strategy, TestConfiguration configuration, UUID reportId, ReportRepository repository) {
        StrategyTestObject test = new StrategyTestObject(configuration, strategy, reportId, repository);
        pastDataTestingStrategies.offer(test);
    }

    public synchronized StrategyTestObject getNextStrategyTest() throws InterruptedException {
        return pastDataTestingStrategies.take();
    }
}