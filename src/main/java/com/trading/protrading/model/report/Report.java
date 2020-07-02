package com.trading.protrading.model.report;

import com.trading.protrading.model.Strategy;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "uuid", columnDefinition = "BINARY(16)")
    private UUID publicId;
    private Double totalReturn;
    private Double winLossRatio;
    private Double profitFactor;
    @ManyToOne
    @JoinColumn(nullable = false, name = "strategyId", referencedColumnName = "id")
    private Strategy strategy;
    private Double maxDrawdown;
    private Double returnToDrawdown;
    private Integer maxConsecutiveLosses;
}