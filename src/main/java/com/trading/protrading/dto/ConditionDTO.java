package com.trading.protrading.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trading.protrading.data.strategy.Predicate;
import com.trading.protrading.model.Condition;
import lombok.Data;

@Data
public class ConditionDTO {
    private Double assetPrice;
    private String predicate;

    @JsonIgnore
    public Predicate getPredicateEnum() {
        if (this.predicate.compareTo(">=") == 0) {
            return Predicate.GREATER_OR_EQUAL;
        }
        if (this.predicate.compareTo(">") == 0) {
            return Predicate.GREATER_THAN;
        }
        if (this.predicate.compareTo("<=") == 0) {
            return Predicate.LESS_OR_EQUAL;
        }
        if (this.predicate.compareTo("<") == 0) {
            return Predicate.LESS_THAN;
        }
        throw new IllegalStateException("Invalid predicate"); //TODO
    }
}
