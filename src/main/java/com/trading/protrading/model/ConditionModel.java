package com.trading.protrading.model;

import com.trading.protrading.model.rule.RuleModel;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "conditions")
public class ConditionModel {

    @Id
    @GeneratedValue
    private Long id;
    private double assetPrice;
    private String predicate;
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "ruleId")
    private RuleModel ruleModel;
}
