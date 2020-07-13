package com.trading.protrading.backtesting;

import org.junit.Test;

import static org.junit.Assert.*;

public class TradeTest {

    @Test
    public void testOpenAndCheckFundsSpentChange() {
        Trade trade = new Trade();
        trade.open(15.3, 39.52);
        assertEquals(39.52, trade.getFundsSpend(), 2);
    }

    @Test
    public void testOpenAndCheckAssetAmountChange() {
        Trade trade = new Trade();
        trade.open(15.2, 39.52);
        assertEquals(2.6, trade.getAssetAmount(), 2);
    }

    @Test
    public void testClose() {
        Trade trade = new Trade();
        trade.open(15.2, 39.52);
        assertEquals(45.5, trade.close(17.5), 2);
    }

    @Test
    public void testCloseFundsSpentAndAssetAmountCleared() {
        Trade trade = new Trade();
        trade.open(15.2, 39.52);
        trade.close(17.5);
        assertEquals(0, trade.getAssetAmount(), 2);
        assertEquals(0, trade.getFundsSpend(), 2);
    }

    @Test
    public void testGetOpeningPrice() {
        Trade trade = new Trade();
        trade.open(15.2, 39.52);
        assertEquals(15.2, trade.getOpeningPrice(), 2);
    }

    @Test
    public void testIsOpen() {
        Trade trade = new Trade();
        trade.open(15.2, 39.52);
        assertTrue(trade.isOpen());
        trade.close(15);
        assertFalse(trade.isOpen());
    }
}