package com.trading.protrading.backtesting;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.data.strategy.QuoteType;
import com.trading.protrading.model.Strategy;
import com.trading.protrading.model.report.Report;
import com.trading.protrading.repository.ReportRepository;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StrategyTestObjectTest {
    public static final int STARTING_FUNDS = 1000;
    public static final double DELTA = 0.01;
    public static final int CLOSING_PRICE = 35;
    public static final int STOP_LOSS = 20;
    private TestConfiguration configuration = new TestConfiguration("User1",
            "GoldStrategy",
            Asset.GOLD,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(5),
            STARTING_FUNDS,
            150);
    private Quote quote = new Quote(Asset.GOLD, 12.3, QuoteType.BUY, LocalDateTime.now().plusHours(1));

    @Test
    public void testGetIdentifier() {
        StrategyTestObject testObject = new StrategyTestObject(configuration, null, null, null);
        TestIdentifier id = new TestIdentifier("User1", "GoldStrategy");
        assertEquals(id, testObject.getIdentifier());

        TestIdentifier falseId = new TestIdentifier("User2", "GoldStrategy");
        assertNotEquals(falseId, testObject.getIdentifier());
    }

    @Test
    public void testExecuteWithIncorrectAsset() {
        ReportRepository repository = mock(ReportRepository.class);
        Strategy strategy = mock(Strategy.class);
        StrategyTestObject testObject = new StrategyTestObject(configuration, strategy, null, repository);

        Quote quote = new Quote(Asset.PETROL, 12.3, QuoteType.BUY, LocalDateTime.now());

        testObject.execute(quote);
        verify(repository, never()).save(any(Report.class));
        verify(strategy, never()).execute(any(Quote.class), any(StrategyTestObject.class));
    }

    @Test
    public void testExecuteWithIncorrectQuoteEndTimeAndOpenTrade() {
        ReportRepository repository = mock(ReportRepository.class);
        Strategy strategy = mock(Strategy.class);
        StrategyTestObject testObject = new StrategyTestObject(configuration, strategy, null, repository);

        testObject.openTrade(new Quote(null, 52, null, null), 12);
        assertTrue(testObject.tradeIsOpen());

        testObject.execute(quote);
        verify(repository, times(1)).save(any(Report.class));
        verify(strategy, never()).execute(any(Quote.class), any(StrategyTestObject.class));
        assertFalse(testObject.tradeIsOpen());
    }

    @Test
    public void testExecuteWithIncorrectQuoteEndTimeAndClosedTrade() {
        ReportRepository repository = mock(ReportRepository.class);
        Strategy strategy = mock(Strategy.class);
        StrategyTestObject testObject = new StrategyTestObject(configuration, strategy, null, repository);

        testObject.execute(quote);
        verify(repository, times(1)).save(any(Report.class));
        verify(strategy, never()).execute(any(Quote.class), any(StrategyTestObject.class));
        assertFalse(testObject.tradeIsOpen());
    }

    @Test
    public void testExecuteWithCorrectQuoteEndTime() {
        ReportRepository repository = mock(ReportRepository.class);
        Strategy strategy = mock(Strategy.class);
        StrategyTestObject testObject = new StrategyTestObject(configuration, strategy, null, repository);
        Quote quote = new Quote(Asset.GOLD, 12.3, QuoteType.BUY, LocalDateTime.now());
        testObject.execute(quote);
        verify(repository, never()).save(any(Report.class));
        verify(strategy, times(1)).execute(quote, testObject);
    }

    @Test
    public void testOpenTradeWithNotEnoughFunds() {
        StrategyTestObject testObject = new StrategyTestObject(configuration, null, null, null);
        assertEquals(0, testObject.getLockedFunds(), DELTA);
        testObject.openTrade(quote, STARTING_FUNDS + 200);
        assertEquals(0, testObject.getLockedFunds(), DELTA);
    }

    @Test
    public void testOpenTradeWithEnoughFundsLocksFunds() {
        StrategyTestObject testObject = new StrategyTestObject(configuration, null, null, null);
        assertEquals(0, testObject.getLockedFunds(), DELTA);
        testObject.openTrade(quote, STOP_LOSS);
        assertEquals(243.9, testObject.getLockedFunds(), DELTA);
    }

    @Test
    public void testOpenTradeWithEnoughFundsModifiesFunds() {
        StrategyTestObject testObject = new StrategyTestObject(configuration, null, null, null);
        testObject.openTrade(quote, STOP_LOSS);
        assertEquals(606.1, testObject.getFunds(), DELTA);
    }

    @Test
    public void testOpenTradeWithEnoughFundsOpensTrade() {
        StrategyTestObject testObject = new StrategyTestObject(configuration, null, null, null);
        assertFalse(testObject.tradeIsOpen());
        testObject.openTrade(quote, STOP_LOSS);
        assertTrue(testObject.tradeIsOpen());
    }

    @Test
    public void testOpenTradeWithEnoughFundsModifiesRawReport() {
        StrategyTestObject testObject = new StrategyTestObject(configuration, null, null, null);
        assertEquals(STARTING_FUNDS, testObject.getRawReport().getCurrentFunds(), DELTA);
        testObject.openTrade(quote, STOP_LOSS);
        assertEquals(850, testObject.getRawReport().getCurrentFunds(), DELTA);
    }

    @Test
    public void testCloseTradeUpdatesFunds() {
        StrategyTestObject testObject = new StrategyTestObject(configuration, null, null, null);
        assertEquals(STARTING_FUNDS, testObject.getFunds(), DELTA);
        testObject.openTrade(quote, STOP_LOSS);
        testObject.closeTrade(CLOSING_PRICE);
        assertEquals(1276.83, testObject.getFunds(), DELTA);
    }

    @Test
    public void testCloseTradeClearsLockedFunds() {
        StrategyTestObject testObject = new StrategyTestObject(configuration, null, null, null);
        testObject.openTrade(quote, STOP_LOSS);
        testObject.closeTrade(CLOSING_PRICE);
        assertEquals(0, testObject.getLockedFunds(), DELTA);
    }

    @Test
    public void testCloseTradeClosesTrade() {
        StrategyTestObject testObject = new StrategyTestObject(configuration, null, null, null);
        assertFalse(testObject.tradeIsOpen());
        testObject.openTrade(quote, STOP_LOSS);
        assertTrue(testObject.tradeIsOpen());
        testObject.closeTrade(CLOSING_PRICE);
        assertFalse(testObject.tradeIsOpen());
    }

    @Test
    public void testCloseTradeUpdatesRawReport() {
        StrategyTestObject testObject = new StrategyTestObject(configuration, null, null, null);
        assertEquals(STARTING_FUNDS, testObject.getRawReport().getCurrentFunds(), DELTA);
        testObject.openTrade(quote, STOP_LOSS);
        testObject.closeTrade(CLOSING_PRICE);
        assertEquals(1276.83, testObject.getRawReport().getCurrentFunds(), DELTA);
    }
}