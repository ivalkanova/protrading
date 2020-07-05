package com.trading.protrading.backtesting;

import org.junit.Test;

import static org.junit.Assert.*;

public class TradeTest {

    public static final double DELTA = 0.01;
    public static final double OPENING_PRICE = 15.2;
    public static final double BUY_FUNDS = 39.52;
    public static final double CLOSE_PRICE = 17.5;
    public static final double RECEIVED_FUNDS = 45.5;

    @Test
    public void testOpenAndCheckFundsSpentChange() {
        Trade trade = new Trade();
        trade.open(OPENING_PRICE, BUY_FUNDS);
        assertEquals(39.52, trade.getFundsSpend(), DELTA);
    }

    @Test
    public void testOpenAndCheckAssetAmountChange() {
        Trade trade = new Trade();
        trade.open(OPENING_PRICE, BUY_FUNDS);
        assertEquals(2.6, trade.getAssetAmount(), DELTA);
    }

    @Test
    public void testClose() {
        Trade trade = new Trade();
        trade.open(OPENING_PRICE, BUY_FUNDS);
        assertEquals(RECEIVED_FUNDS, trade.close(CLOSE_PRICE), DELTA);
    }

    @Test
    public void testCloseFundsSpentAndAssetAmountCleared() {
        Trade trade = new Trade();
        trade.open(OPENING_PRICE, BUY_FUNDS);
        trade.close(CLOSE_PRICE);
        assertEquals(0, trade.getAssetAmount(), DELTA);
        assertEquals(0, trade.getFundsSpend(), DELTA);
    }

    @Test
    public void testCloseFundsReceivedModified() {
        Trade trade = new Trade();
        trade.open(OPENING_PRICE, BUY_FUNDS);
        trade.close(CLOSE_PRICE);
        assertEquals(RECEIVED_FUNDS, trade.getFundsReceived(),DELTA);


    }

    @Test
    public void testGetOpeningPrice() {
        Trade trade = new Trade();
        trade.open(OPENING_PRICE, BUY_FUNDS);
        assertEquals(OPENING_PRICE, trade.getOpeningPrice(), DELTA);
    }

    @Test
    public void testIsOpen() {
        Trade trade = new Trade();
        trade.open(OPENING_PRICE, BUY_FUNDS);
        assertTrue(trade.isOpen());
        trade.close(CLOSE_PRICE);
        assertFalse(trade.isOpen());
    }
}