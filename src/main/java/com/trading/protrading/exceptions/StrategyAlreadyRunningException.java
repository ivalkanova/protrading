package com.trading.protrading.exceptions;

public class StrategyAlreadyRunningException extends Exception {
    public StrategyAlreadyRunningException(String strategyName) {
        super(strategyName);
    }
}
