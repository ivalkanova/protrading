package com.trading.protrading.client.models;

public class Condition {

    private double assetPrice;
    private String predicate;

    public Condition(double assetPrice, String predicate) {
        this.assetPrice = assetPrice;
        this.predicate = predicate;
    }

    @Override
    public String toString() {
        return "Condition: " + this.predicate + " " + this.assetPrice;
    }
}
