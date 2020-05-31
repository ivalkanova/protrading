package com.trading.protrading.model.rule;

import com.trading.protrading.model.Condition;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "rules")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Rule {

    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL)
    private Set<Condition> conditions;

}
