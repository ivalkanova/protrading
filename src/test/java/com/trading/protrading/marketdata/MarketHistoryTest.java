package com.trading.protrading.marketdata;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.exceptions.InvalidPeriodException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MarketHistoryTest {

    @Test
    public void getQuotesWithEndDateToday() {
        MarketHistory marketHistory = new MarketHistory();
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusDays(1);
        assertThrows(InvalidPeriodException.class,
                () -> marketHistory.getQuotes(start, end, Asset.SILVER));
    }

    @Test
    public void getQuotesWithStartDateToday() {
        MarketHistory marketHistory = new MarketHistory();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);
        assertThrows(InvalidPeriodException.class,
                () -> marketHistory.getQuotes(start, end, Asset.SILVER));
    }

    @Test
    public void getQuotesWithEndDateBeforeStart() {
        MarketHistory marketHistory = new MarketHistory();
        LocalDateTime start = LocalDateTime.of(2020, 6, 3, 3, 30, 30);
        LocalDateTime end = start.minusDays(1);
        assertThrows(InvalidPeriodException.class,
                () -> marketHistory.getQuotes(start, end, Asset.SILVER));
    }

}
