package com.trading.protrading.backtesting;

public class RawReport {
   /* Return -  The total return of the strategy since it started running.
            Win/loss ratio - Total wins over total losses since the strategy started running.
    Profit factor - Gross profits over gross losses since the strategy started running.
    Max drawdown - Defines the maximum drop from peak to valley, described in percentage from the highest peak.
    Return to drawdown - The average return for a strategy, expressed as proportion of the maximum drawdown level.
    Max consecutive losses - The maximum consecutive losses the strategy had since it started running.*/

    private double maxFunds;
    private double currentFunds;
    private double lockedFunds;
    private double grossProfit;
    private double grossLosses;
    //private double strategyReturn;
    private double maxDrawdown; // = 1 - (currentFunds/maxFunds)
    private double drawdownReturn; // = ret / maxDrawdown
    private int maxConsecutiveLossesCount;
    private int currentConsecutiveLossesCount;

    public RawReport(double funds) {
        this.maxFunds = funds;
        this.currentFunds = funds;
        this.lockedFunds=0;
        this.grossProfit = 0;
        this.grossLosses = 0;
        //this.strategyReturn = 0;
        this.maxDrawdown = 0;
        this.drawdownReturn = 0;
        this.maxConsecutiveLossesCount = 0;
        this.currentConsecutiveLossesCount = 0;
    }

    public void openPosition(double funds) {
        currentFunds -= funds;
        lockedFunds=funds;
    }

    public void closePosition(double outcome) {
        updateFunds(outcome);
        updateGrossOutcomes(outcome);
        updateDrawdown();
    }

    private void updateDrawdown() {
        maxDrawdown=1-(currentFunds/maxFunds);
        drawdownReturn=currentFunds/maxDrawdown;
    }

    private void updateGrossOutcomes(double outcome) {
        double grossOutcome = outcome-lockedFunds;
        if(grossOutcome>=0){
            grossProfit += grossOutcome;
        }
        else{
            grossLosses-=grossOutcome;
            currentConsecutiveLossesCount++;
            maxConsecutiveLossesCount = Math.max(maxConsecutiveLossesCount,currentConsecutiveLossesCount);
        }
    }

    private void updateFunds(double outcome) {
        currentFunds += outcome;
        maxFunds = Math.max(maxFunds, currentFunds);
    }

    public double getCurrentFunds() {
        return currentFunds;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public double getGrossLosses() {
        return grossLosses;
    }

    public double getMaxDrawdown() {
        return maxDrawdown;
    }

    public double getDrawdownReturn() {
        return drawdownReturn;
    }

    public int getMaxConsecutiveLossesCount() {
        return maxConsecutiveLossesCount;
    }
}
