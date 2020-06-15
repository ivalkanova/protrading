package com.trading.protrading.backtesting;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.Strategy;

import java.time.LocalDateTime;
import java.util.Objects;

public class StrategyTest {
    private TestIdentifier identifier;
    private Strategy strategy;
    private LocalDateTime start;
    private LocalDateTime end;
    private RawReport rawReport;
    private Long reportId;

    public StrategyTest(String username, Strategy strategy,LocalDateTime start, LocalDateTime end, Long reportId) {
       this.identifier =new TestIdentifier(username, strategy.getName());
        this.strategy = strategy;
        this.start=start;
        this.end = end;
        this.rawReport=new RawReport(strategy.getFunds());
        this.reportId = reportId;
    }

    public StrategyTest(TestIdentifier identifier) {
        this.identifier = identifier;
        strategy=null;
        start=null;
        end=null;
        rawReport=null;
        reportId=null;
    }

    public TestIdentifier getIdentifier() {
        return identifier;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public RawReport getRawReport() {
        return rawReport;
    }

    public Long getReportId() {
        return reportId;
    }

    public Asset getAsset(){
        return strategy.getAsset();
    }

    public String getStrategyName(){
        return strategy.getName();
    }

    public void execute(double price){
        //TODO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StrategyTest that = (StrategyTest) o;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
