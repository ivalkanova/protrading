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

    public void open(double currentPrice, double buyFunds) {
        fundsSpend = buyFunds;
        assetAmount = buyFunds / currentPrice;
    }

    public double close(double currentPrice) {
        fundsReceived = assetAmount * currentPrice;
        assetAmount = 0;
        fundsSpend = 0;
        return fundsReceived;
    }

    public double getAssetAmount() {
        return assetAmount;
    }

    public double getFundsSpend() {
        return fundsSpend;
    }

    public double getFundsReceived() {
        return fundsReceived;
    }

    public double getOpeningPrice() {
        return fundsSpend / assetAmount;
    }

    public boolean isOpen() {
        return assetAmount != 0;
    }
}