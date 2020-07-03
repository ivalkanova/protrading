package com.trading.protrading.backtesting;

import com.trading.protrading.data.strategy.Asset;

import java.time.LocalDateTime;

public class TestConfiguration {
    private String username;
    private String strategyName;
    private Asset asset;
    private LocalDateTime start;
    private LocalDateTime end;
    private double funds;
    private double transactionBuyFunds;

    public TestConfiguration(String username,
                             String strategyName,
                             Asset asset,
                             LocalDateTime start,
                             LocalDateTime end,
                             double funds,
                             double transactionBuyFunds) {
        this.username = username;
        this.strategyName = strategyName;
        this.asset = asset;
        this.start = start;
        this.end = end;
        this.funds = funds;
        this.transactionBuyFunds = transactionBuyFunds;
    }

    public Asset getAsset() {
        return asset;
    }

    public String getUsername() {
        return username;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public LocalDateTime getStart() {
        return start == null ? LocalDateTime.now() : start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public double getFunds() {
        return funds;
    }

    public double getTransactionBuyFunds() {
        return transactionBuyFunds;
    }
}