package com.trading.protrading.data.strategy;

import java.util.Set;

public class Strategy {

    private String name;
    private Asset asset;
    private double buyFundsPerTrade;
    private Set<Rule> rules;
    private double funds;

    public Strategy(String name, Asset asset, double buyFundsPerTrade, Set<Rule> rules, double funds) {
        this.name = name;
        this.asset = asset;
        this.buyFundsPerTrade = buyFundsPerTrade;
        this.rules = rules;
        this.funds = funds;
    }

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
