package com.trading.protrading.model;

import com.trading.protrading.data.strategy.Predicate;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "conditions")
public class Condition {

    @Id
    @GeneratedValue
    private Long id;
    private double assetPrice;
    private Predicate predicate;

    public Condition(double assetPrice, Predicate predicate) {
        this.assetPrice = assetPrice;
        this.predicate = predicate;
    }

    public boolean checkPredicate(double price) {
        switch (predicate) {
            case LESS_THAN:
                return price < assetPrice;
            case LESS_OR_EQUAL:
                return price <= assetPrice;
            case GREATER_THAN:
                return price > assetPrice;
            case GREATER_OR_EQUAL:
                return price >= assetPrice;
            default:
                throw new IllegalStateException("Unexpected value: " + predicate);
        }
    }
}