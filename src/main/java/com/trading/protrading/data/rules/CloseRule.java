package com.trading.protrading.data.rules;

import com.trading.protrading.data.Strategy;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
public class CloseRule extends Rule {

    @OneToOne
    @JoinColumn(name = "strategyId", referencedColumnName = "id")
    private Strategy strategy;
}
