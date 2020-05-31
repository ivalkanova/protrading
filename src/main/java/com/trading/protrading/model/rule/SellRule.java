package com.trading.protrading.model.rule;

import com.trading.protrading.model.Strategy;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class SellRule extends Rule {

    @OneToOne
    @JoinColumn(name = "strategyId", referencedColumnName = "sellRuleStrategyId")
    private Strategy strategy;
}
