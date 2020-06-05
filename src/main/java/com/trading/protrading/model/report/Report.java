package com.trading.protrading.model.report;

import com.trading.protrading.model.Strategy;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue
    private Long id;
    private Double totalReturn;
    private Double winLossRatio;
    private Double profitFactor;
    @ManyToOne
    @JoinColumn(nullable = false, name = "strategyId", referencedColumnName = "id")
    private Strategy strategy;
}
