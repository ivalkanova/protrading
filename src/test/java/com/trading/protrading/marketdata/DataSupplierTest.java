package com.trading.protrading.marketdata;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.data.strategy.QuoteType;

import com.trading.protrading.exceptions.InvalidPeriodException;
import com.trading.protrading.generators.QuoteGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class DataSupplierTest {

    @Mock
    private QuoteGenerator generator;
    @Mock
    private Archive goldArchive;
    private static List<Quote> goldQuotes;

    @BeforeAll
    public static void setUp() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 3, 3, 30, 30);
        LocalDateTime endDate = startDate.plusDays(3);
        goldQuotes = new ArrayList<>();
        goldQuotes.add(new Quote(Asset.GOLD, 20, QuoteType.BUY, startDate));
        goldQuotes.add(new Quote(Asset.GOLD, 20, QuoteType.BUY, endDate));
        goldQuotes.add(new Quote(Asset.GOLD, 20, QuoteType.BUY, endDate));
        goldQuotes.add(new Quote(Asset.GOLD, 20, QuoteType.BUY, endDate.plusMinutes(3)));

    }

    @Test
    public void getOldQuotes() throws InvalidPeriodException {
        Map<Asset, Archive> archives = new HashMap<>();
        archives.put(Asset.GOLD, goldArchive);
        DataSupplier dataSupplier = new DataSupplier(archives, generator);
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 3, 3, 30, 30);
        LocalDateTime endDate = startDate.plusDays(3);
        when(goldArchive.getOldQuotes(startDate.toLocalDate(), endDate.toLocalDate()))
                .thenReturn(goldQuotes);
        List<Quote> expected = goldQuotes;
        expected.remove(goldQuotes.size() - 1);
        assertIterableEquals(dataSupplier.getOldQuotes(startDate, endDate, Asset.GOLD), expected,
                "The actual quotes are not in the range start end");
    }

    @Test
    public void getOldQuotesEndIsToday() {
        DataSupplier dataSupplier = new DataSupplier();

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime start = today.minusDays(1);
        assertThrows(InvalidPeriodException.class,
                () -> dataSupplier.getOldQuotes(start, today, Asset.GOLD),
                "Start or end cannot be today");
    }

}
