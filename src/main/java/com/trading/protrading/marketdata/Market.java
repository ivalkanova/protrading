package com.trading.protrading.marketdata;

import com.trading.protrading.data.strategy.Quote;

public class Market {

    private DataSupplier dataSupplier;

    public Market() {
        this(new DataSupplier());
    }

    Market(DataSupplier dataSupplier) {
        this.dataSupplier = dataSupplier;
    }

    public Quote getQuote() {
        return dataSupplier.getLatestQuote();
    }

}
