package com.trading.protrading.generators;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.data.strategy.QuoteType;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class QuoteGenerator {

    private final static double AVERAGE_SILVER_PRICE = 0.53;
    private final static double AVERAGE_GOLD_PRICE = 50.89;
    private final static double AVERAGE_PETROL_PRICE = 42.66;
    private final Map<Asset, Double> latestPrices;
    private final NumberGenerator numberGenerator;

    public QuoteGenerator() {
        latestPrices = new EnumMap<>(Asset.class);
        latestPrices.put(Asset.PETROL, AVERAGE_PETROL_PRICE);
        latestPrices.put(Asset.GOLD, AVERAGE_GOLD_PRICE);
        latestPrices.put(Asset.SILVER, AVERAGE_SILVER_PRICE);
        numberGenerator = new NumberGenerator();
    }

    QuoteGenerator(Map<Asset, Double> latestPrices, NumberGenerator numberGenerator) {
        this.latestPrices = latestPrices;
        this.numberGenerator = numberGenerator;
    }

    public Quote generateQuote() {

        int assetIndex = numberGenerator.generateInt(0, Asset.values().length - 1);
        Asset asset = Asset.values()[assetIndex];

        int typeIndex = numberGenerator.generateInt(0, QuoteType.values().length - 1);
        QuoteType type = QuoteType.values()[typeIndex];

        double price = generatePrice(asset, type);
        latestPrices.put(asset, price);

        return new Quote(asset, price, type, LocalDateTime.now());
    }

    private double generatePrice(Asset asset, QuoteType type) {
        double latestPrice = latestPrices.get(asset);
        double variation = generateVariation(type, latestPrice);
        double price;
        if (type.equals(QuoteType.BUY)) {
            price = numberGenerator.generateDouble(latestPrice, variation + latestPrice);
        } else {
            price = numberGenerator.generateDouble(latestPrice - variation, latestPrice);
        }
        return round(price);
    }

    private double generateVariation(QuoteType type, double lastPrice) {
        double maximumVariation = 20;
        if (lastPrice <= maximumVariation && type.equals(QuoteType.SELL)) {
            maximumVariation = lastPrice - 1;
        }
        return numberGenerator.generateDouble(0, maximumVariation);
    }

    private double round(double number) {
        int factor = 100;
        return (double) Math.round(number * factor) / factor;
    }

}
