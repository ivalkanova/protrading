package com.trading.protrading.data;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Report {
    private @Id Long id;

    private Double totalReturn;
    private Double winLossRatio;
    private Double profitFactor;
    @ManyToOne
    //@JoinColumn(name = "strategyId", referencedColumnName = "id")
    private Strategy strategy;
}
