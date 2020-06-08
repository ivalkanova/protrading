package testdata;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.exceptions.InvalidPeriodException;

import java.time.LocalDateTime;
import java.util.Collection;


public class MarketHistory {


    public Collection<Quote> getQuotes(LocalDateTime start, LocalDateTime end, Asset asset) throws InvalidPeriodException {
        return null;
    }
}
