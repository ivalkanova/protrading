package com.trading.protrading.strategytesting;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.model.Strategy;
import com.trading.protrading.model.report.Report;
import com.trading.protrading.repository.ReportRepository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class StrategyTestTask {
    private final TestIdentifier identifier;
    private final Strategy strategy;
    private TestConfiguration configuration;
    private double funds;
    private final RawReport rawReport;
    private final UUID reportId;
    private final Trade trade;
    private double lockedFunds;
    private ReportRepository repository;
    private boolean finished;
    private Quote lastOpenQuote;

    public StrategyTestTask(TestConfiguration configuration,
                            Strategy strategy,
                            UUID reportId,
                            ReportRepository repository) {
        this.identifier = new TestIdentifier(configuration.getUsername(), configuration.getStrategyName());
        this.strategy = strategy;
        this.configuration = configuration;
        this.funds = configuration.getFunds();
        this.rawReport = new RawReport(configuration.getFunds());
        this.reportId = reportId;
        this.trade = new Trade();
        this.lockedFunds = 0;
        this.repository = repository;
        this.finished = false;
    }

    public StrategyTestTask(TestIdentifier identifier) {
        this.identifier = identifier;
        strategy = null;
        rawReport = null;
        reportId = null;
        trade = null;
        this.finished = false;
    }

    public TestIdentifier getIdentifier() {
        return identifier;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public LocalDateTime getStart() {
        return configuration.getStart();
    }

    public LocalDateTime getEnd() {
        return configuration.getEnd();
    }

    public double getFunds() {
        return funds;
    }

    public Asset getAsset() {
        return configuration.getAsset();
    }

    public RawReport getRawReport() {
        return rawReport;
    }

    public UUID getReportId() {
        return reportId;
    }

    public Trade getTrade() {
        return trade;
    }

    public double getTransactionBuyFunds() {
        return configuration.getTransactionBuyFunds();
    }

    public double getLockedFunds() {
        return lockedFunds;
    }

    public boolean isFinished() {
        return finished;
    }

    public void execute(Quote quote) {
        if (quote.getDate().isBefore(configuration.getEnd())) {
            strategy.execute(quote, this);
        } else {
            finalizeTestingObject();
        }
    }

    private void finalizeTestingObject() {
        if (trade.isOpen()) {
            closeTrade(lastOpenQuote.getPrice());
        }
        saveReport();
        finished = true;
    }

    private void saveReport() {
        Report finalReport = new Report(rawReport, reportId);
        finalReport.setStrategy(this.strategy);
        repository.save(finalReport);
    }

    public void openTrade(Quote quote, double stopLossForOneAsset) {
        double transactionBuyFunds = configuration.getTransactionBuyFunds();
        double stopLoss = stopLossForOneAsset * (transactionBuyFunds / quote.getPrice());

        if (funds < stopLoss) {
            return;
        } else {
            double buyFunds = Math.min(transactionBuyFunds, funds - stopLoss);
            updateFunds(stopLoss, buyFunds);
            trade.open(quote.getPrice(), buyFunds);
            rawReport.openPosition(buyFunds);
            lastOpenQuote = quote;
        }
    }

    private void updateFunds(double stopLoss, double buyFunds) {
        funds -= buyFunds + stopLoss;
        lockedFunds = stopLoss;
    }

    public void closeTrade(double price) {
        double outcome = trade.close(price);
        funds += outcome + lockedFunds;
        lockedFunds = 0;
        rawReport.closePosition(outcome);
    }

    public double getTradeOpeningPrice() {
        return trade.getOpeningPrice();
    }

    public boolean tradeIsOpen() {
        return trade.isOpen();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StrategyTestTask that = (StrategyTestTask) o;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}