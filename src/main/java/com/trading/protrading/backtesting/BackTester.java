package com.trading.protrading.backtesting;

import com.trading.protrading.model.Strategy;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

public class BackTester {
    private ArrayBlockingQueue<StrategyTestObject> pastDataTestingStrategies;

    public synchronized void enableStrategy(Strategy strategy, TestConfiguration configuration, UUID reportId) {
        StrategyTestObject test = new StrategyTestObject(configuration, strategy, reportId);
        pastDataTestingStrategies.offer(test);
    }

    public synchronized StrategyTestObject getNextStrategyTest() throws InterruptedException {
        return pastDataTestingStrategies.take();
    }
}