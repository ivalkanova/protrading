package com.trading.protrading.backtesting;

import com.trading.protrading.data.strategy.Strategy;
import com.trading.protrading.exceptions.StrategyNotFoundException;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;

public class BackTester {
    private ArrayBlockingQueue<StrategyTest> liveTestingStrategies;
    private final Object liveTestingLock = new Object();
    private ArrayBlockingQueue<StrategyTest> pastDataTestingStrategies;
    private final Object pastDataTestingLock = new Object();


    public void enableStrategy(Strategy strategy, TestConfiguration configuration, Long reportId) {
        synchronized (liveTestingLock) {
            StrategyTest test = new StrategyTest(configuration.getUsername(),
                    strategy,
                    null,
                    configuration.getEnd(),
                    reportId);
            liveTestingStrategies.offer(test);
        }
    }

    public StrategyTest getNextLiveStrategyTest() throws InterruptedException {
        synchronized (liveTestingLock) {
            StrategyTest currentTest = liveTestingStrategies.take();
            if (currentTest.getEnd().isAfter(LocalDateTime.now())) {
                liveTestingStrategies.offer(currentTest);
            }
            return currentTest;
        }
    }

    public void runOnPastData(Strategy strategy, TestConfiguration configuration, Long reportId) {
        synchronized (pastDataTestingLock) {
            StrategyTest test = new StrategyTest(configuration.getUsername(),
                    strategy,
                    configuration.getStart(),
                    configuration.getEnd(),
                    reportId);
            pastDataTestingStrategies.offer(test);
        }
    }

    public StrategyTest getNextPastDataStrategyTest() throws InterruptedException {
        synchronized (pastDataTestingLock) {
            return pastDataTestingStrategies.take();
        }
    }

    public void disableStrategy(String username, String strategy) throws StrategyNotFoundException {
        synchronized (liveTestingLock) {
            StrategyTest test = new StrategyTest(new TestIdentifier(username, strategy));
            boolean result = liveTestingStrategies.remove(test);
            if (result == false) {
                throw new StrategyNotFoundException("There isn't enabled strategy with the specified name. Please check for typos.");
            }
        }
    }
}
