package com.trading.protrading.service;

import com.trading.protrading.backtesting.TestConfiguration;
import com.trading.protrading.data.strategy.ComparisonReport;
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
import java.util.Collection;

@Service
public class BacktestingService {
    private ConditionRepository conditionRepository;
    private ReportRepository<Report> standartReportRepository;
    private ReportRepository<ExtendedReport> extendedReportRepository;
    private RuleRepository ruleRepository;
    private StrategyRepository strategyRepository;


    public BacktestingService(ConditionRepository conditionRepository,
                              ReportRepository<Report> standartReportRepository,
                              ReportRepository<ExtendedReport> extendedReportRepository,
                              RuleRepository ruleRepository,
                              StrategyRepository strategyRepository) {
        this.conditionRepository = conditionRepository;
        this.standartReportRepository = standartReportRepository;
        this.extendedReportRepository = extendedReportRepository;
        this.ruleRepository = ruleRepository;
        this.strategyRepository = strategyRepository;
    }

    public BigInteger enableStrategy(TestConfiguration testConfiguration)
            throws StrategyNotFoundException{
        return null;
    }

    public ComparisonReport compareStrategies(String username, String firstStrategy, String secondStrategy)throws StrategyNotFoundException{
        return null;
    }

    public void disableStrategy(String username, String strategy)throws StrategyNotFoundException{

    }

    public Collection<Report> getReports(String username, String strategy, int countOfReports) throws StrategyNotFoundException {
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
