package com.trading.protrading.model;

import com.trading.protrading.backtesting.StrategyTestTask;
import com.trading.protrading.data.strategy.Predicate;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.data.strategy.QuoteType;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class RuleTest {
    public static final double OPPENING_PRICE = 25.7;
    private Condition condition = new Condition(25.3, Predicate.LESS_OR_EQUAL);
    private Rule rule = new Rule(condition, 10.9, 6.4);

    @Test
    public void testExecuteWhenTradeIsNotOpenAndPriceIsNotSatisfactory() {
        StrategyTestTask strategyTest = mock(StrategyTestTask.class);
        when(strategyTest.tradeIsOpen()).thenReturn(false);
        Quote quote = new Quote(null, 28, QuoteType.BUY, null);
        rule.execute(quote,strategyTest);
        verify(strategyTest,times(0)).openTrade(quote,10.9);
    }

    @Test
    public void testExecuteWhenTradeIsOpenAndPriceIsNotSatisfactory() {
        StrategyTestTask strategyTest = mock(StrategyTestTask.class);
        when(strategyTest.tradeIsOpen()).thenReturn(true);
        when(strategyTest.getTradeOpeningPrice()).thenReturn(OPPENING_PRICE);
        Quote quote = new Quote(null, 28, QuoteType.SELL, null);
        rule.execute(quote,strategyTest);
        verify(strategyTest,times(0)).closeTrade(28);
    }

    @Test
    public void testExecuteWhenTradeIsNotOpenAndPriceIsSatisfactory() {
        StrategyTestTask strategyTest = mock(StrategyTestTask.class);
        when(strategyTest.tradeIsOpen()).thenReturn(false);
        Quote quote = new Quote(null, 20.2, QuoteType.BUY, null);
        rule.execute(quote,strategyTest);
        verify(strategyTest,times(1)).openTrade(quote,10.9);
    }

    @Test
    public void testExecuteWhenTradeIsOpenAndPriceIsExceedingStopLossBoundary() {
        StrategyTestTask strategyTest = mock(StrategyTestTask.class);
        when(strategyTest.tradeIsOpen()).thenReturn(true);
        when(strategyTest.getTradeOpeningPrice()).thenReturn(OPPENING_PRICE);
        Quote quote = new Quote(null, 13.5, QuoteType.SELL, null);
        rule.execute(quote,strategyTest);
        verify(strategyTest,times(1)).closeTrade(13.5);
    }

    @Test
    public void testExecuteWhenTradeIsOpenAndPriceIsExceedingTakeProfitBoundary() {
        StrategyTestTask strategyTest = mock(StrategyTestTask.class);
        when(strategyTest.tradeIsOpen()).thenReturn(true);
        when(strategyTest.getTradeOpeningPrice()).thenReturn(OPPENING_PRICE);
        Quote quote = new Quote(null, 40, QuoteType.SELL, null);
        rule.execute(quote,strategyTest);
        verify(strategyTest,times(1)).closeTrade(40.0);
    }

}