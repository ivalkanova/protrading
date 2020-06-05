package com.trading.protrading.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rules")
public class Rule {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "id", name = "conditionId")
    private Condition condition;
    private Double stopLoss;
    private Double takeProfit;
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "strategyId")
    private Strategy strategy;
}
