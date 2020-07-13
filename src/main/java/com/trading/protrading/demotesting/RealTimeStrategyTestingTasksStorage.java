package com.trading.protrading.demotesting;

import com.trading.protrading.exceptions.StrategyNotFoundException;
import com.trading.protrading.model.Strategy;
import com.trading.protrading.repository.ReportRepository;
import com.trading.protrading.strategytesting.StrategyTestTask;
import com.trading.protrading.strategytesting.TestConfiguration;
import com.trading.protrading.strategytesting.TestIdentifier;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

public class RealTimeStrategyTestingTasksStorage {
    public static final int CAPACITY = 1000;
    private ArrayBlockingQueue<StrategyTestTask> testingStrategies;

    public RealTimeStrategyTestingTasksStorage() {
        testingStrategies = new ArrayBlockingQueue<StrategyTestTask>(CAPACITY);
    }

    public synchronized void enableStrategy(Strategy strategy, TestConfiguration configuration, UUID reportId, ReportRepository repository) {
        StrategyTestTask test = new StrategyTestTask(configuration, strategy, reportId, repository);
        testingStrategies.offer(test);
    }

    public synchronized StrategyTestTask getNextTask() {
        StrategyTestTask currentTest = null;
        try {
            currentTest = testingStrategies.take();
        } catch (InterruptedException e) {
            //TODO What should I do here???
        }

        while (currentTest.isFinished()) {
            try {
                currentTest = testingStrategies.take();
            } catch (InterruptedException e) {
                //TODO What should I do here???
            }
        }

        testingStrategies.offer(currentTest);
        return currentTest;
    }

    public synchronized void putDelimiter() {
        TestIdentifier identifier = new TestIdentifier("delimiter", "delimiter");
        StrategyTestTask delimiter = new StrategyTestTask(identifier);
        testingStrategies.offer(delimiter);
    }

    public synchronized void disableStrategy(String username, String strategy) throws StrategyNotFoundException {
        StrategyTestTask test = new StrategyTestTask(new TestIdentifier(username, strategy));
        boolean result = testingStrategies.remove(test);
        if (result == false) {
            throw new StrategyNotFoundException(
                    "There isn't enabled strategy with the specified name. Please check for typos.");
        }
    }
}