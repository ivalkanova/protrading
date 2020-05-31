package com.trading.protrading.model.report;

import com.trading.protrading.model.Strategy;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "reports")
public abstract class Report {

    private @Id
    Long id;
    private Double totalReturn;
    private Double winLossRatio;
    private Double profitFactor;
    @ManyToOne
    //@JoinColumn(name = "strategyId", referencedColumnName = "id")
    private Strategy strategy;
}
