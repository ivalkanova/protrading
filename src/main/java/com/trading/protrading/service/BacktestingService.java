package com.trading.protrading.service;

import com.trading.protrading.backtesting.BackTester;
import com.trading.protrading.backtesting.PastDataStrategyTestingTasksStorage;
import com.trading.protrading.marketdata.MarketHistory;
import com.trading.protrading.strategytesting.TestConfiguration;
import com.trading.protrading.data.strategy.ComparisonReport;
import com.trading.protrading.exceptions.IncompatibleReportTypesException;
import com.trading.protrading.exceptions.ReportNotFoundException;
import com.trading.protrading.exceptions.StrategyNotFoundException;
import com.trading.protrading.model.Strategy;
import com.trading.protrading.model.report.Report;
import com.trading.protrading.repository.ReportRepository;
import com.trading.protrading.repository.StrategyRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.UUID;

@Service
public class BacktestingService {
    private ReportRepository reportRepository;
    private StrategyRepository strategyRepository;
    private PastDataStrategyTestingTasksStorage storage;


    public BacktestingService(ReportRepository reportRepository,
                              StrategyRepository strategyRepository) {
        this.reportRepository = reportRepository;
        this.strategyRepository = strategyRepository;
        this.storage = new PastDataStrategyTestingTasksStorage();
        BackTester tester = new BackTester(this.storage, new MarketHistory());
        tester.start();
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
        storage.enableStrategy(strategy, testConfiguration, reportId, reportRepository);
        return reportId;
    }

   // public ComparisonReport compareStrategies(String username, String firstStrategy, String secondStrategy)
   //         throws StrategyNotFoundException {
   //     return null;
   // }

    public Collection<Report> getReports(String username, String strategy, int countOfReports)
            throws StrategyNotFoundException {
        return null;
    }

    public Report getReport(UUID reportId) throws ReportNotFoundException {
        return null;
    }

    public ComparisonReport compareReports(UUID firstReportId, UUID secondReportId)
            throws IncompatibleReportTypesException, ReportNotFoundException {
        return null;
    }

}