package com.trading.protrading.client.models;

public class Rule {
    private double stopLoss;
    private double takeProfit;
    private Condition condition;

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
