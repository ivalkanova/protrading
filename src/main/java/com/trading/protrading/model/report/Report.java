package com.trading.protrading.model.report;

import com.trading.protrading.backtesting.RawReport;
import com.trading.protrading.model.Strategy;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "uuid", columnDefinition = "BINARY(16)")
    @ManyToOne
    @JoinColumn(nullable = false, name = "strategyId", referencedColumnName = "id")
    private Strategy strategy;
    private UUID publicId;
    private Double totalReturn;
    private Double winLossRatio;
    private Double profitFactor;
    private Double maxDrawdown;
    private Double returnToDrawdown;
    private Integer maxConsecutiveLosses;

    public Report(RawReport raw, UUID reportId) {
        publicId = reportId;
        extractRawReportData(raw);
    }

    private void extractRawReportData(RawReport raw) {
        totalReturn = raw.getCurrentFunds();
        winLossRatio = (double) raw.getWinCount() / raw.getLossesCount();
        profitFactor = raw.getGrossProfit() / raw.getGrossLosses();
        maxDrawdown = raw.getMaxDrawdown();
        returnToDrawdown = raw.getDrawdownReturn();
        maxConsecutiveLosses = raw.getMaxConsecutiveLossesCount();
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public Double getTotalReturn() {
        return totalReturn;
    }

    public Double getWinLossRatio() {
        return winLossRatio;
    }

    public Double getProfitFactor() {
        return profitFactor;
    }

    public Double getMaxDrawdown() {
        return maxDrawdown;
    }

    public Double getReturnToDrawdown() {
        return returnToDrawdown;
    }

    public Integer getMaxConsecutiveLosses() {
        return maxConsecutiveLosses;
    }
}