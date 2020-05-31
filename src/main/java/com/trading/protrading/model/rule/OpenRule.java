package com.trading.protrading.model.rule;

import com.trading.protrading.model.Strategy;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
public class OpenRule extends Rule {

    @OneToOne
    @JoinColumn(name = "strategyId", referencedColumnName = "id")
    private Strategy strategy;
}
