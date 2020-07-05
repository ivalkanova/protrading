package com.trading.protrading.generators;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.data.strategy.TransactionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuoteGeneratorTest {

    @Mock
    private NumberGenerator numberGenerator;
    private final static Map<Asset, Double> LATEST_PRICES = new HashMap<>();

    @BeforeAll
    public static void setUp() {
        LATEST_PRICES.put(Asset.PETROL, 42.66);
        LATEST_PRICES.put(Asset.GOLD, 50.89);
        LATEST_PRICES.put(Asset.SILVER, 0.53);
    }

    @Test
    public void generateQuoteWithTransactionTypeSell() {
        final int silverIndex = 1;
        final int typeSell = 1;
        final double silverLatestPrice = 0.53;

        when(numberGenerator.generateInt(anyInt(), anyInt()))
                .thenReturn(silverIndex)
                .thenReturn(typeSell)
                .thenAnswer(returnsSecondArg());
        when(numberGenerator.generateDouble(anyDouble(), anyDouble())).thenAnswer(returnsSecondArg());

        LATEST_PRICES.put(Asset.SILVER, silverLatestPrice);
        QuoteGenerator generator = new QuoteGenerator(LATEST_PRICES, numberGenerator);
        Quote quote = generator.generateQuote();

        assertEquals(Asset.SILVER, quote.getAsset());
        assertEquals(TransactionType.SELL, quote.getType());
        assertTrue(quote.getPrice() <= silverLatestPrice,
                "Price should stay the same or decrease on SELL");
    }

    @Test
    public void generateQuoteWithTransactionTypeBuy() {
        final int silverIndex = 1;
        final int typeBuy = 0;
        final double silverLastestPrice = 0.53;

        when(numberGenerator.generateInt(anyInt(), anyInt()))
                .thenReturn(silverIndex)
                .thenReturn(typeBuy)
                .thenAnswer(returnsSecondArg());
        when(numberGenerator.generateDouble(anyDouble(), anyDouble())).thenAnswer(returnsSecondArg());

        LATEST_PRICES.put(Asset.SILVER, silverLastestPrice);
        QuoteGenerator generator = new QuoteGenerator(LATEST_PRICES, numberGenerator);
        Quote quote = generator.generateQuote();

        assertEquals(Asset.SILVER, quote.getAsset());
        assertEquals(TransactionType.BUY, quote.getType());
        assertTrue(quote.getPrice() >= silverLastestPrice,
                "Price should stay the same or increase on BUY");

    }

    @Test
    public void generateQuotePriceIsPositive() {
        when(numberGenerator.generateInt(anyInt(), anyInt())).thenAnswer(returnsSecondArg());
        when(numberGenerator.generateDouble(anyDouble(), anyDouble())).thenAnswer(returnsSecondArg());

        QuoteGenerator generator = new QuoteGenerator(LATEST_PRICES, numberGenerator);
        Quote quote = generator.generateQuote();

        assertTrue(quote.getPrice() > 0, "Quote price should be positive");

    }

}
