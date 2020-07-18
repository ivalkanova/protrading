package com.trading.protrading.data.strategy;

import com.trading.protrading.model.report.Report;

import java.util.UUID;

public class ComparisonReport {
    private UUID publicIdFirst;
    private Double totalReturnFirst;
    private Double winLossRatioFirst;
    private Double profitFactorFirst;
    private Double maxDrawdownFirst;
    private Double returnToDrawdownFirst;
    private Integer maxConsecutiveLossesFirst;

    private UUID publicIdSecond;
    private Double totalReturnSecond;
    private Double winLossRatioSecond;
    private Double profitFactorSecond;
    private Double maxDrawdownSecond;
    private Double returnToDrawdownSecond;
    private Integer maxConsecutiveLossesSecond;

    public ComparisonReport(Report firstReport, Report secondReport){
        this.publicIdFirst = firstReport.getPublicId();
        this.totalReturnFirst = firstReport.getTotalReturn();
        this.winLossRatioFirst = firstReport.getWinLossRatio();
        this.profitFactorFirst = firstReport.getProfitFactor();
        this.maxDrawdownFirst = firstReport.getMaxDrawdown();
        this.returnToDrawdownFirst = firstReport.getReturnToDrawdown();
        this.maxConsecutiveLossesFirst = firstReport.getMaxConsecutiveLosses();

        this.publicIdSecond = secondReport.getPublicId();
        this.totalReturnSecond = secondReport.getTotalReturn();
        this.winLossRatioSecond = secondReport.getWinLossRatio();
        this.profitFactorSecond = secondReport.getProfitFactor();
        this.maxDrawdownSecond = secondReport.getMaxDrawdown();
        this.returnToDrawdownSecond = secondReport.getReturnToDrawdown();
        this.maxConsecutiveLossesSecond = secondReport.getMaxConsecutiveLosses();
    }
}
