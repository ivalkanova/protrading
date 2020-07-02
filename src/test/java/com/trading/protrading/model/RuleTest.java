package com.trading.protrading.model;

import com.trading.protrading.backtesting.StrategyTestObject;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.data.strategy.QuoteType;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class RuleTest {
    private Condition condition = new Condition(25.3, "<=");
    private Rule rule = new Rule(condition, 10.9, 6.4);

    @Test
    public void testExecuteWhenTradeIsNotOpenAndPriceIsNotSatisfactory() {
        StrategyTestObject strategyTest = mock(StrategyTestObject.class);
        when(strategyTest.tradeIsOpen()).thenReturn(false);
        Quote quote = new Quote(null, 28, QuoteType.BUY, null);
        rule.execute(quote,strategyTest);
        verify(strategyTest,times(0)).openTrade(quote,10.9);
    }

    @Test
    public void testExecuteWhenTradeIsOpenAndPriceIsNotSatisfactory() {
        StrategyTestObject strategyTest = mock(StrategyTestObject.class);
        when(strategyTest.tradeIsOpen()).thenReturn(true);
        when(strategyTest.getTradeOpeningPrice()).thenReturn(25.7);
        Quote quote = new Quote(null, 28, QuoteType.SELL, null);
        rule.execute(quote,strategyTest);
        verify(strategyTest,times(0)).closeTrade(28);
    }

    @Test
    public void testExecuteWhenTradeIsNotOpenAndPriceIsSatisfactory() {
        StrategyTestObject strategyTest = mock(StrategyTestObject.class);
        when(strategyTest.tradeIsOpen()).thenReturn(false);
        Quote quote = new Quote(null, 20.2, QuoteType.BUY, null);
        rule.execute(quote,strategyTest);
        verify(strategyTest,times(1)).openTrade(quote,10.9);
    }

    @Test
    public void testExecuteWhenTradeIsOpenAndPriceIsExceedingStopLossBoundary() {
        StrategyTestObject strategyTest = mock(StrategyTestObject.class);
        when(strategyTest.tradeIsOpen()).thenReturn(true);
        when(strategyTest.getTradeOpeningPrice()).thenReturn(25.7);
        Quote quote = new Quote(null, 13.5, QuoteType.SELL, null);
        rule.execute(quote,strategyTest);
        verify(strategyTest,times(1)).closeTrade(13.5);
    }

    @Test
    public void testExecuteWhenTradeIsOpenAndPriceIsExceedingTakeProfitBoundary() {
        StrategyTestObject strategyTest = mock(StrategyTestObject.class);
        when(strategyTest.tradeIsOpen()).thenReturn(true);
        when(strategyTest.getTradeOpeningPrice()).thenReturn(25.7);
        Quote quote = new Quote(null, 40, QuoteType.SELL, null);
        rule.execute(quote,strategyTest);
        verify(strategyTest,times(1)).closeTrade(40.0);
    }

}