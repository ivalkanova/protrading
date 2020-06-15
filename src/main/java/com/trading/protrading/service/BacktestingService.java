package com.trading.protrading.service;

import com.trading.protrading.backtesting.BackTester;
import com.trading.protrading.backtesting.TestConfiguration;
import com.trading.protrading.data.strategy.ComparisonReport;
import com.trading.protrading.data.strategy.ReportType;
import com.trading.protrading.data.strategy.Strategy;
import com.trading.protrading.exceptions.IncompatibleReportTypesException;
import com.trading.protrading.exceptions.ReportNotFoundException;
import com.trading.protrading.exceptions.StrategyNotFoundException;
import com.trading.protrading.model.report.ExtendedReport;
import com.trading.protrading.model.report.Report;
import com.trading.protrading.repository.ConditionRepository;
import com.trading.protrading.repository.ReportRepository;
import com.trading.protrading.repository.RuleRepository;
import com.trading.protrading.repository.StrategyRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class BacktestingService {
    private ConditionRepository conditionRepository;
    private ReportRepository<Report> standardReportRepository;
    private ReportRepository<ExtendedReport> extendedReportRepository;
    private RuleRepository ruleRepository;
    private StrategyRepository strategyRepository;
    private BackTester backTester;


    public BacktestingService(ConditionRepository conditionRepository,
                              ReportRepository<Report> standardReportRepository,
                              ReportRepository<ExtendedReport> extendedReportRepository,
                              RuleRepository ruleRepository,
                              StrategyRepository strategyRepository) {
        this.conditionRepository = conditionRepository;
        this.standardReportRepository = standardReportRepository;
        this.extendedReportRepository = extendedReportRepository;
        this.ruleRepository = ruleRepository;
        this.strategyRepository = strategyRepository;
        this.backTester=new BackTester();
    }

    public BigInteger enableStrategy(TestConfiguration testConfiguration)
            throws StrategyNotFoundException {
        ReportType type = testConfiguration.getReportType();
        Report report;
        if (type == ReportType.STANDART) {
            report = standardReportRepository.save(new Report());
        } else {
            report = extendedReportRepository.save(new ExtendedReport());
        }

        //TODO get strategy
//        StrategyModel strategyModel = strategyRepository.getAllByNameAndUser_UserName(testConfiguration.getStrategyName(),
//                testConfiguration.getUsername()).get(0);
        Strategy strategy = null;
        if (testConfiguration.getStart()== LocalDateTime.now()) {
            backTester.enableStrategy(strategy, testConfiguration, report.getId());
        }
        else{

        }
        return null;
    }

    public ComparisonReport compareStrategies(String username, String firstStrategy, String secondStrategy)
            throws StrategyNotFoundException {
        return null;
    }

    public void disableStrategy(String username, String strategy) throws StrategyNotFoundException {
        backTester.disableStrategy(username,strategy);
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
