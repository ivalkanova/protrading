package com.trading.protrading.data.strategy;

import java.util.Set;

public class Strategy {

    private String name;
    private Asset asset;
    private double transactionBuyFunds;
    //delete  private int transactionSellAmount;
    private Set<Rule> rules;

}
