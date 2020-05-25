package com.trading.protrading.model.rule;

import com.trading.protrading.model.ConditionModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "rules")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class RuleModel {

    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "ruleModel", cascade = CascadeType.ALL)
    private Set<ConditionModel> conditionModels;

}
