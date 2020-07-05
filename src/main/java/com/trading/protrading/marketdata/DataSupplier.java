package com.trading.protrading.marketdata;

import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.generators.QuoteGenerator;

public class DataSupplier {

    private final QuoteGenerator generator;

    public DataSupplier() {
        this(new QuoteGenerator());
    }

    DataSupplier(QuoteGenerator generator) {
        this.generator = generator;
    }

    public Quote getLatestQuote() {
        return generator.generateQuote();
    }
}
