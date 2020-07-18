package com.trading.protrading.model;

import com.trading.protrading.strategytesting.StrategyTestTask;
import com.trading.protrading.data.strategy.Predicate;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.data.strategy.QuoteType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class StrategyTest {

    private Strategy strategy;

    @Before
    public void init() {
        Condition condition1 = new Condition(25.2, Predicate.LESS_THAN);
        Condition condition2 = new Condition(20, Predicate.GREATER_THAN);
        Condition condition3 = new Condition(24, Predicate.LESS_OR_EQUAL);

        Rule rule1 = new Rule(condition1, 10.0, 15.0);
        Rule rule2 = new Rule(condition2, 17.0, 25.0);
        Rule rule3 = new Rule(condition3, 12.0, 19.0);

        Set<Rule> rules = new HashSet<>();
        rules.add(rule1);
        rules.add(rule2);
        rules.add(rule3);
        strategy = new Strategy("My strategy", rules);
    }

    @Test
    public void testExecuteWithPriceSatisfactoryForAllRules() {
        StrategyTestTask strategyTest = mock(StrategyTestTask.class);
        when(strategyTest.tradeIsOpen()).thenReturn(true);
        when(strategyTest.getTradeOpeningPrice()).thenReturn(25.7);

        Quote quote = new Quote(null, 50.8, QuoteType.SELL, null);

        strategy.execute(quote,strategyTest);

        verify(strategyTest,times(3)).closeTrade(50.8);
    }

    @Test
    public void testExecuteWithPriceNotSatisfactoryForAnyRule() {
        StrategyTestTask strategyTest = mock(StrategyTestTask.class);
        when(strategyTest.tradeIsOpen()).thenReturn(true);
        when(strategyTest.getTradeOpeningPrice()).thenReturn(25.7);

        Quote quote = new Quote(null, 40.1, QuoteType.SELL, null);

        strategy.execute(quote,strategyTest);

        verify(strategyTest,times(0)).closeTrade(40.1);
    }

    @Test
    public void testExecuteWithPriceSatisfactoryForSomeRules() {
        StrategyTestTask strategyTest = mock(StrategyTestTask.class);
        when(strategyTest.tradeIsOpen()).thenReturn(false);

        Quote quote = new Quote(null, 24.8, QuoteType.BUY, null);

        strategy.execute(quote,strategyTest);

        verify(strategyTest,times(1)).openTrade(quote,10.0);
        verify(strategyTest,times(1)).openTrade(quote,17.0);
    }
}