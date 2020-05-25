package com.trading.protrading.model.rule;

import com.trading.protrading.model.StrategyModel;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class BuyRuleModel extends RuleModel {

    @OneToOne
    @JoinColumn(name = "strategyId", referencedColumnName = "id")
    private StrategyModel strategyModel;
}
