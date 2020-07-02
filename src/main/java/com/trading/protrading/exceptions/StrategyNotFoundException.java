package com.trading.protrading.exceptions;

public class StrategyNotFoundException extends Exception{
    public StrategyNotFoundException(String message) {
        super(message);
    }

    public StrategyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
