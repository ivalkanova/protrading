package com.trading.protrading.data.strategy;

import java.util.Set;

public class Strategy {

    private String name;
    private Asset asset;
    private double buyFundsPerTrade;
    //delete  private int transactionSellAmount;
    private Set<Rule> rules;
    private double funds;

    public String getName() {
        return name;
    }

    public Asset getAsset() {
        return asset;
    }

    public double getBuyFundsPerTrade() {
        return buyFundsPerTrade;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public double getFunds() {
        return funds;
    }
}
