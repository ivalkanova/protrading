package com.trading.protrading.backtesting;

import com.trading.protrading.exceptions.StrategyNotFoundException;
import com.trading.protrading.model.Strategy;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

public class DemoTester {
    private ArrayBlockingQueue<StrategyTestObject> testingStrategies;

    public DemoTester() {
        testingStrategies = new ArrayBlockingQueue<StrategyTestObject>(10000);
    }

    public synchronized void enableStrategy(Strategy strategy, TestConfiguration configuration, UUID reportId) {
        StrategyTestObject test = new StrategyTestObject(configuration, strategy, reportId);
        testingStrategies.offer(test);
    }

    public synchronized StrategyTestObject getNextStrategyTest() throws InterruptedException {
        StrategyTestObject currentTest = testingStrategies.take();
        if (currentTest.getEnd().isAfter(LocalDateTime.now())) {
            testingStrategies.offer(currentTest);
        }
        return currentTest;
    }

    public synchronized void disableStrategy(String username, String strategy) throws StrategyNotFoundException {
        StrategyTestObject test = new StrategyTestObject(new TestIdentifier(username, strategy));
        boolean result = testingStrategies.remove(test);
        if (result == false) {
            throw new StrategyNotFoundException("There isn't enabled strategy with the specified name. Please check for typos.");
        }
    }
}