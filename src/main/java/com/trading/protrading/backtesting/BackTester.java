package com.trading.protrading.backtesting;

import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.exceptions.InvalidPeriodException;
import com.trading.protrading.strategytesting.StrategyTestTask;
import testdata.MarketHistory;

import java.util.LinkedList;

public class BackTester extends Thread {
    private final PastDataStrategyTestingTasksStorage pastDataStrategyTestingTasksStorage;
    private final MarketHistory marketHistory;
    private LinkedList<Quote> quotes;

    public BackTester(PastDataStrategyTestingTasksStorage pastDataStrategyTestingTasksStorage, MarketHistory marketHistory) {
        this.pastDataStrategyTestingTasksStorage = pastDataStrategyTestingTasksStorage;
        this.marketHistory = marketHistory;
        this.quotes = null;
    }

    @Override
    public void run() {
        StrategyTestTask currentTask = pastDataStrategyTestingTasksStorage.getNextStrategyTest();
        while (currentTask != null) {
            executeTaskOverQuotes(currentTask);

            finalizeTask(currentTask, quotes);
            currentTask = pastDataStrategyTestingTasksStorage.getNextStrategyTest();
        }
    }


    private void executeTaskOverQuotes(StrategyTestTask task) {
        try {
            quotes = new LinkedList<>(marketHistory.getQuotes(task.getStart(), task.getEnd(), task.getAsset()));
        } catch (InvalidPeriodException e) {
            e.printStackTrace();
        }

        for (Quote quote : quotes) {
            task.execute(quote);
        }
    }

    private void finalizeTask(StrategyTestTask task, LinkedList<Quote> quotes) {
        Quote quote = quotes.getLast();
        Quote closingQuote = new Quote(quote.getAsset(), 0, null, quote.getDate().plusMinutes(1));
        task.execute(closingQuote);
    }
}

