package com.trading.protrading.generators;

import java.util.concurrent.ThreadLocalRandom;

public class NumberGenerator {

    public int generateInt(int minimum, int maximum) {
        int range = maximum - minimum + 1;
        return (int) (Math.random() * range) + minimum;
    }

    public double generateDouble(double minimum, double maximum) {
        double range = maximum-minimum;
        return Math.random()*range +minimum;
    }

}
