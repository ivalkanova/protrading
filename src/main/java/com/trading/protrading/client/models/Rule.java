package com.trading.protrading.client.models;

public class Rule {
    private double stopLoss;
    private double takeProfit;
    private Condition condition;

    public Rule(double stopLoss,double takeProfit,Condition condition){
        this.stopLoss=stopLoss;
        this.takeProfit=takeProfit;
        this.condition=condition;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stop Loss: ");
        sb.append(stopLoss);
        sb.append('\n');
        sb.append("Take Profit: ");
        sb.append(takeProfit);
        sb.append('\n');
        sb.append(this.condition.toString());

        return sb.toString();
    }
}
