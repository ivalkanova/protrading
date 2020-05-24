package com.trading.protrading.data.rules;

import com.trading.protrading.data.Strategy;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class BuyRule extends Rule {
    @OneToOne
    @JoinColumn(name = "strategyId", referencedColumnName = "id")
    private Strategy strategy;
}
