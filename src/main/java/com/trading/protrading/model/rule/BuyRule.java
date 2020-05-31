package com.trading.protrading.model.rule;

import com.trading.protrading.model.Strategy;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class BuyRule extends Rule {

    @OneToOne
    @JoinColumn(name = "strategyId", referencedColumnName = "id")
    private Strategy strategy;
}
