package com.trading.protrading.model.rule;

import com.trading.protrading.model.StrategyModel;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class SellRuleModel extends RuleModel {

    @OneToOne
    @JoinColumn(name = "strategyId", referencedColumnName = "sellRuleStrategyId")
    private StrategyModel strategyModel;
}
