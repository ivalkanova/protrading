package com.trading.protrading.demotesting;

import com.trading.protrading.exceptions.StrategyNotFoundException;
import com.trading.protrading.model.Strategy;
import com.trading.protrading.repository.ReportRepository;
import com.trading.protrading.strategytesting.StrategyTestTask;
import com.trading.protrading.strategytesting.TestConfiguration;
import com.trading.protrading.strategytesting.TestIdentifier;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.logging.log4j.Logger;

import static org.apache.logging.log4j.LogManager.getLogger;

public class RealTimeStrategyTestingTasksStorage {
    private final static Logger LOGGER = getLogger(RealTimeStrategyTestingTasksStorage.class);
    private static final int CAPACITY = 1000;
    private ArrayBlockingQueue<StrategyTestTask> testingStrategies;
    private StrategyTestTask delimiter;

    public RealTimeStrategyTestingTasksStorage() {
        testingStrategies = new ArrayBlockingQueue<StrategyTestTask>(CAPACITY);
        TestIdentifier identifier = new TestIdentifier("delimiter", "delimiter");
        delimiter = new StrategyTestTask(identifier);
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
            LOGGER.debug(e.getMessage());
        }

        //skip working on and remove already finished tasks
        while (currentTest.isFinished()) {
            try {
                currentTest = testingStrategies.take();
            } catch (InterruptedException e) {
                LOGGER.debug(e.getMessage());
            }
        }

        if (!currentTest.equals(delimiter)) {
            testingStrategies.offer(currentTest);
        }

        return currentTest;
    }

    public synchronized void putDelimiter() {
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

    public synchronized boolean isRunning(String username, String strategy) {
        TestIdentifier testIdentifier = new TestIdentifier(username, strategy);
        StrategyTestTask task = new StrategyTestTask(testIdentifier);
        for (StrategyTestTask runningTask : testingStrategies) {
            if (task.equals(runningTask)) {
                return true;
            }
        }
        return false;
    }
}