package com.trading.protrading.marketdata;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.exceptions.InvalidPeriodException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class MarketHistory {

    private final DataSupplier dataSupplier;

    public MarketHistory() {
        this(new DataSupplier());
    }

    MarketHistory(DataSupplier dataSupplier) {
        this.dataSupplier = dataSupplier;
    }

    public Collection<Quote> getQuotes(LocalDateTime start, LocalDateTime end, Asset asset)
            throws InvalidPeriodException {

        if (start == null || end == null) {
            throw new IllegalArgumentException("Parameters start and end cannot be null");
        }
        LocalDate today = LocalDate.now();
        if (today.equals(start.toLocalDate()) || today.equals(end.toLocalDate())) {
            throw new InvalidPeriodException("Parameters start and end cannot be todays date");
        }
        if (start.isAfter(end)) {
            throw new InvalidPeriodException("The start date and time must be before the end");
        }
        return dataSupplier.getOldQuotes(start, end, asset);
    }

}
