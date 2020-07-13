package com.trading.protrading.marketdata;

import com.trading.protrading.data.strategy.Quote;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyArchive {

    private final Map<LocalDate, List<Quote>> dailyQuotes;

    public DailyArchive() {
        this(new HashMap<>());
    }

    DailyArchive(Map<LocalDate, List<Quote>> dailyQuotes) {
        this.dailyQuotes = dailyQuotes;
    }

    public List<Quote> getQuotes(LocalDate date) {
        if (dailyQuotes.containsKey(date)) {
            return dailyQuotes.get(date);
        }
        return new ArrayList<>();
    }

    public void addLatestQuote(Quote quote) {
        LocalDate date = quote.getDate().toLocalDate();
        List<Quote> quotes;
        if (!dailyQuotes.containsKey(date)) {
            quotes = new ArrayList<>();
            quotes.add(quote);
        } else {
            quotes = dailyQuotes.get(date);
            quotes.add(quote);
        }
        dailyQuotes.put(date, quotes);
    }

    public void deleteQuotes(LocalDate date) {
        dailyQuotes.remove(date);
    }

}
