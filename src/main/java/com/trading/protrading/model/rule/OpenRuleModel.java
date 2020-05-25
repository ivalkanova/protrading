package com.trading.protrading.model.rule;

import com.trading.protrading.model.StrategyModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
public class OpenRuleModel extends RuleModel {

    @OneToOne
    @JoinColumn(name = "strategyId", referencedColumnName = "id")
    private StrategyModel strategyModel;
}
