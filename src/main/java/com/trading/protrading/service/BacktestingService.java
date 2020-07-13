package com.trading.protrading.service;

import com.trading.protrading.backtesting.BackTester;
import com.trading.protrading.backtesting.TestConfiguration;
import com.trading.protrading.data.strategy.ComparisonReport;
import com.trading.protrading.exceptions.IncompatibleReportTypesException;
import com.trading.protrading.exceptions.ReportNotFoundException;
import com.trading.protrading.exceptions.StrategyNotFoundException;
import com.trading.protrading.model.Strategy;
import com.trading.protrading.model.report.Report;
import com.trading.protrading.repository.ConditionRepository;
import com.trading.protrading.repository.ReportRepository;
import com.trading.protrading.repository.RuleRepository;
import com.trading.protrading.repository.StrategyRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.UUID;

@Service
public class BacktestingService {
    private ReportRepository reportRepository;
    private StrategyRepository strategyRepository;
    private BackTester backTester;


    public BacktestingService(ReportRepository reportRepository,
                              StrategyRepository strategyRepository) {
        this.reportRepository = reportRepository;
        this.strategyRepository = strategyRepository;
        this.backTester = new BackTester();
    }

    public UUID enableStrategy(TestConfiguration testConfiguration)
            throws StrategyNotFoundException {
        Strategy strategy;
        try{
         strategy = strategyRepository.getAllByNameAndUser_UserName(testConfiguration.getStrategyName(),
                testConfiguration.getUsername()).get(0);}
        catch (IndexOutOfBoundsException e){
            throw new StrategyNotFoundException("Strategy with name " + testConfiguration.getStrategyName() +" was not found.", e);
        }
        UUID reportId = UUID.randomUUID();
        backTester.enableStrategy(strategy, testConfiguration, reportId);
        return reportId;
    }

    public ComparisonReport compareStrategies(String username, String firstStrategy, String secondStrategy)
            throws StrategyNotFoundException {
        return null;
    }

    public Collection<Report> getReports(String username, String strategy, int countOfReports)
            throws StrategyNotFoundException {
        return null;
    }

    public Report getReport(String username, BigInteger reportId) throws ReportNotFoundException {
        return null;
    }

    public ComparisonReport compareReports(String username, BigInteger firstReportId, BigInteger secondReportId)
            throws IncompatibleReportTypesException, ReportNotFoundException {
        return null;
    }

}