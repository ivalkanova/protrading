package com.trading.protrading.demotesting;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.strategytesting.StrategyTestTask;
import com.trading.protrading.strategytesting.TestIdentifier;
import testdata.Market;

public class DemoTester extends Thread {
    private Market market;
    private RealTimeStrategyTestingTasksStorage taskStorage;
    private StrategyTestTask delimiter;
    private Quote currentQuote;
    private StrategyTestTask currentTask;

    public DemoTester(Market market, RealTimeStrategyTestingTasksStorage taskStorage) {
        super();
        this.market = market;
        this.taskStorage = taskStorage;
        this.delimiter = new StrategyTestTask(new TestIdentifier("delimiter", "delimiter"));
    }

    @Override
    public void run() {
        currentQuote = market.getQuote();
        while (currentQuote != null) {
            executeAllAvailableTasks();
            currentQuote = market.getQuote();
        }
    }

    private void executeAllAvailableTasks() {
        taskStorage.putDelimiter();
        currentTask = taskStorage.getNextTask();

        while (!currentTask.equals(delimiter)) {
            Asset currentTestObjectAsset = currentTask.getAsset();
            Asset quoteAsset = currentQuote.getAsset();

            if (currentTestObjectAsset.equals(quoteAsset)) {
                currentTask.execute(currentQuote);
            }

            currentTask = taskStorage.getNextTask();
        }
    }
}
