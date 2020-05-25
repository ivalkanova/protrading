package com.trading.protrading.service;

import com.trading.protrading.data.Candlestick;

import java.time.LocalDateTime;
import java.util.List;

public class TestDataService {

    public List<Candlestick> get(LocalDateTime start, LocalDateTime end, int duration) {
        return null;
    }

    public List<Candlestick> get(LocalDateTime start, LocalDateTime end) {
        return get(start, end, 1);
    }
}
