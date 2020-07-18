package com.trading.protrading.client.models;

public class Condition {
    private double assetPrice;
    private String predicate;


    @Override
    public String toString() {
        return "Condition: " + this.predicate + " " + this.assetPrice;
    }
}
