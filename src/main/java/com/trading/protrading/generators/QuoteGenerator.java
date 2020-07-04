package com.trading.protrading.generators;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.data.strategy.TransactionType;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class QuoteGenerator {

    private final static double AVERAGE_SILVER_PRICE = 0.53;
    private final static double AVERAGE_GOLD_PRICE = 50.89;
    private final static double AVERAGE_PETROL_PRICE = 42.66;
    private final Map<Asset, Double> lastPrices;
    private final NumberGenerator numberGenerator;

    public QuoteGenerator() {
        lastPrices = new EnumMap<>(Asset.class);
        lastPrices.put(Asset.PETROL, AVERAGE_PETROL_PRICE);
        lastPrices.put(Asset.GOLD, AVERAGE_GOLD_PRICE);
        lastPrices.put(Asset.SILVER, AVERAGE_SILVER_PRICE);
        numberGenerator = new NumberGenerator();
    }

    QuoteGenerator(Map<Asset, Double> lastPrices, NumberGenerator numberGenerator) {
        this.lastPrices = lastPrices;
        this.numberGenerator = numberGenerator;
    }

    public Quote generateQuote() {

        int assetIndex = numberGenerator.generateInt(0, Asset.values().length - 1);
        Asset asset = Asset.values()[assetIndex];

        int typeIndex = numberGenerator.generateInt(0, TransactionType.values().length - 1);
        TransactionType type = TransactionType.values()[typeIndex];

        double price = generatePrice(asset, type);
        lastPrices.put(asset, price);

        return new Quote(asset, price, type, LocalDateTime.now());
    }

    private double generatePrice(Asset asset, TransactionType type) {
        double lastPrice = lastPrices.get(asset);
        double variation = generateVariation(type, lastPrice);
        double price;
        if (type.equals(TransactionType.BUY)) {
            price = numberGenerator.generateDouble(lastPrice, variation + lastPrice);
        } else {
            price = numberGenerator.generateDouble(lastPrice - variation, lastPrice);
        }
        return round(price);
    }

    private double generateVariation(TransactionType type, double lastPrice) {
        double maximumVariation = 20;
        if (lastPrice <= maximumVariation && type.equals(TransactionType.SELL)) {
            maximumVariation = lastPrice - 1;
        }
        return numberGenerator.generateDouble(0, maximumVariation);
    }

    private double round(double number) {
        int factor = 100;
        return (double) Math.round(number * factor) / factor;
    }

}
