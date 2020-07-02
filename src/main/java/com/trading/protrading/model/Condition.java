package com.trading.protrading.model;

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
    private String predicate;

    public Condition(double assetPrice, String predicate) {
        this.assetPrice = assetPrice;
        this.predicate = predicate;
    }

    public boolean checkPredicate(double price) {
        switch (predicate) {
            case "<":
                return price < assetPrice;
            case "<=":
                return price <= assetPrice;
            case ">":
                return price > assetPrice;
            case ">=":
                return price >= assetPrice;
            default:
                throw new IllegalStateException("Unexpected value: " + predicate);
        }
    }
}