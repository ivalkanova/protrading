package com.trading.protrading.marketdata;

import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.exceptions.InvalidPeriodException;
import com.trading.protrading.generators.QuoteGenerator;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DataSupplier {

    private final Map<Asset, Archive> archives;
    private final QuoteGenerator generator;
    private Quote latestQuote;
    private ScheduledExecutorService updateSheduler;

    public DataSupplier() {
        this(new EnumMap<>(Asset.class), new QuoteGenerator());
        configureArchives();
    }

    DataSupplier(Map<Asset, Archive> archives, QuoteGenerator generator) {
        this.archives = archives;
        this.generator = generator;
        this.latestQuote = generator.generateQuote();
        configureLatestQuoteUpdate();
    }

    private void configureArchives() {
        String workingDirectory = ".";
        String archiveDirectory = "quotes-archive";
        Path location = Path.of(workingDirectory, archiveDirectory);
        for (Asset asset : Asset.values()) {
            archives.put(asset, new Archive(asset, location));
        }
    }

    private void configureLatestQuoteUpdate() {
        int poolSize = 1;
        updateSheduler = Executors.newScheduledThreadPool(poolSize);
        int period = 5;
        long delay = 0;
        updateSheduler.scheduleAtFixedRate(this::updateLatestQuote, delay, period,
                TimeUnit.SECONDS);
    }

    public synchronized Quote getLatestQuote() {
        return latestQuote;
    }

    private void updateLatestQuote() {
        Quote quote = generator.generateQuote();
        synchronized (this) {
            latestQuote = quote;
        }
        Archive archive = archives.get(quote.getAsset());
        archive.addLatestQuote(quote);

    }

    public List<Quote> getOldQuotes(LocalDateTime start, LocalDateTime end, Asset asset)
            throws InvalidPeriodException {
        LocalDate today = LocalDate.now();
        if (today.equals(start.toLocalDate()) || today.equals(end.toLocalDate())) {
            throw new InvalidPeriodException("Parameters start and end cannot be todays date");
        }
        Archive archive = archives.get(asset);
        List<Quote> allQuotes = archive.getOldQuotes(start.toLocalDate(), end.toLocalDate());
        return filterQuotes(allQuotes, start, end);
    }

    private List<Quote> filterQuotes(List<Quote> quotes, LocalDateTime start, LocalDateTime end) {
        return quotes.stream()
                .filter((element) -> !element.getDate().isBefore(start) && !element.getDate()
                        .isAfter(end))
                .collect(Collectors.toList());
    }

    public void stopLatestQuoteUpdate() {
        updateSheduler.shutdown();
    }

}

