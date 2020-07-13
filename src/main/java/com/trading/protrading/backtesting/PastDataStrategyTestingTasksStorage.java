package com.trading.protrading.backtesting;

import com.trading.protrading.model.Strategy;
import com.trading.protrading.repository.ReportRepository;
import com.trading.protrading.strategytesting.StrategyTestTask;
import com.trading.protrading.strategytesting.TestConfiguration;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

public class PastDataStrategyTestingTasksStorage {
    public static final int CAPACITY = 1000;
    private ArrayBlockingQueue<StrategyTestTask> pastDataTestingStrategies;

    public PastDataStrategyTestingTasksStorage() {
        pastDataTestingStrategies=new ArrayBlockingQueue<StrategyTestTask>(CAPACITY);
    }

    public synchronized void enableStrategy(Strategy strategy,
                                            TestConfiguration configuration,
                                            UUID reportId,
                                            ReportRepository repository) {
        StrategyTestTask test = new StrategyTestTask(configuration, strategy, reportId, repository);
        pastDataTestingStrategies.offer(test);
    }

    public synchronized StrategyTestTask getNextStrategyTest() {
        StrategyTestTask testObject = null;
        try {
           testObject = pastDataTestingStrategies.take();
        } catch (InterruptedException e) {
            //TODO What should I do???
        }
        return testObject;
    }
}
