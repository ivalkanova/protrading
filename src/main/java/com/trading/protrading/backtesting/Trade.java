package com.trading.protrading.backtesting;

public class Trade {
    private double fundsSpend;
    private double fundsReceived;
    private double assetAmount;


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

    public double getFundsSpend() {
        return fundsSpend;
    }

    public double getFundsReceived() {
        return fundsReceived;
    }
}
