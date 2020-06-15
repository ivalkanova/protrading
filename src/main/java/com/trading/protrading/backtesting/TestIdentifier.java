package com.trading.protrading.backtesting;

import java.util.Objects;

public class TestIdentifier {
    String username;
    String strategyName;

    public TestIdentifier(String username, String strategyName) {
        this.username = username;
        this.strategyName = strategyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestIdentifier that = (TestIdentifier) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(strategyName, that.strategyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, strategyName);
    }
}
