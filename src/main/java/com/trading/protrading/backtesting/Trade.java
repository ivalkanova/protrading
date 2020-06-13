package com.trading.protrading.backtesting;

import com.trading.protrading.data.strategy.PositionType;

public class Trade {
    private double fundsSpend;
    private double fundsReceived;
    private double assetAmount;
    // private PositionType type;


    public Trade() {
        fundsSpend = 0;
        fundsReceived = 0;
        assetAmount = 0;
    }

    public void open(double price, double amount) {
        fundsSpend = price * amount;
        assetAmount = amount;
    }

    public double close(double price) {
        fundsReceived = assetAmount * price;
        assetAmount = 0;
        fundsSpend = 0;
        return fundsReceived;
    }
}
