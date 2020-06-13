package com.trading.protrading.backtesting;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.ReportType;
import com.trading.protrading.data.strategy.Strategy;

import javax.validation.constraints.AssertTrue;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class StrategyTest {
    private Strategy strategy;
    //private LocalDateTime start;
    private LocalDateTime end;
    private RawReport rawReport;
    private BigInteger reportId;

    public StrategyTest(Strategy strategy, LocalDateTime end, double funds, BigInteger reportId) {
        this.strategy = strategy;
        this.end = end;
        this.rawReport=new RawReport(funds);
        this.reportId = reportId;
    }

    public void execute(double price){
        //TODO
    }

    public Asset getAsset(){
        return strategy.getAsset();
    }
}
