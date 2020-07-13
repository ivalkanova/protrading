package com.trading.protrading.service;


import com.trading.protrading.backtesting.RealTimeStrategyTestingTasksStorage;
import com.trading.protrading.backtesting.TestConfiguration;
import com.trading.protrading.exceptions.StrategyNotFoundException;
import com.trading.protrading.model.Rule;
import com.trading.protrading.model.Strategy;
import com.trading.protrading.repository.ReportRepository;
import com.trading.protrading.repository.StrategyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StrategyService {

    private StrategyRepository strategyRepository;
    private ReportRepository reportRepository;
    private RealTimeStrategyTestingTasksStorage tester;

    public StrategyService(StrategyRepository repository, ReportRepository reportRepository) {
        this.strategyRepository = repository;
        this.reportRepository = reportRepository;
    }

    public void create(Strategy strategy) {
    }

    public void delete(String userName, String strategyName) {
    }

    public Strategy get(String userName, String strategyName) {
        return null;
    }

    public List<Strategy> getAll(String userName) {
        return null;
    }

    public void changeName(String userName, String oldName, String newName) {
    }

    public void changeRules(String userName, String strategyName, List<Rule> rules) {
    }

    public void changeTransactionBuyFunds(String userName, String strategyName, double transactionBuyFunds) {
    }

    public UUID enableStrategy(TestConfiguration testConfiguration)
            throws StrategyNotFoundException {
        Strategy strategy;
        try {
            strategy = strategyRepository.getAllByNameAndUser_UserName(testConfiguration.getStrategyName(),
                    testConfiguration.getUsername()).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new StrategyNotFoundException("Strategy with name " + testConfiguration.getStrategyName() + " was not found.", e);
        }
        UUID reportId = UUID.randomUUID();
        tester.enableStrategy(strategy, testConfiguration, reportId, reportRepository);
        return reportId;
    }

    public void disableStrategy(String username, String strategy) throws StrategyNotFoundException {
        tester.disableStrategy(username, strategy);
    }

}