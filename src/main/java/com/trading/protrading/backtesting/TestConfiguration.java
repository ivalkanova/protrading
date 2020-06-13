package com.trading.protrading.backtesting;

import com.trading.protrading.data.strategy.ReportType;

import java.time.LocalDateTime;

public class TestConfiguration {
   private String username;
   private String strategyName;
   private ReportType reportType;
   private LocalDateTime start;
   private LocalDateTime end;
   private double funds;

    public TestConfiguration(String username, String strategyName, ReportType reportType, LocalDateTime start, LocalDateTime end, double initialFunds) {
        this.username = username;
        this.strategyName = strategyName;
        this.reportType = reportType;
        this.start = start;
        this.end = end;
        this.funds = initialFunds;
    }

    public String getUsername() {
        return username;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public ReportType getReportType() {
        return reportType == null ? ReportType.STANDART : reportType;
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
}
