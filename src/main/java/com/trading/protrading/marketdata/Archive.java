package com.trading.protrading.marketdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trading.protrading.adapters.LocalDateAdapter;
import com.trading.protrading.adapters.LocalDateTimeAdapter;
import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.data.strategy.Quote;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardOpenOption.APPEND;
import static org.apache.logging.log4j.LogManager.getLogger;

public class Archive {

    private static final Logger LOGGER = getLogger(Archive.class);
    private final Path location;
    private final String namePattern;
    private final DailyArchive dailyArchive;
    private final Gson gson;

    public Archive(Asset asset, Path location) {

        if (asset == null) {
            throw new IllegalArgumentException("Parameter asset cannot be null");
        }
        if (location == null) {
            throw new IllegalArgumentException("Parameter path cannot be null");
        }
        this.location = location;
        this.namePattern = asset.name().toLowerCase();
        this.dailyArchive = new DailyArchive();
        this.gson = getGson();
        configure();
    }

    private Gson getGson() {
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return builder.create();
    }

    private void configure() {
        createDirectory(location);
        configureArchiveUpdate();
    }

    private void createDirectory(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void configureArchiveUpdate() {
        int poolSize = 1;
        ScheduledExecutorService sheduler = Executors.newScheduledThreadPool(poolSize);

        int period = 1;
        long delayUntillMidnight = getDelayUntillMidnight();
        sheduler.scheduleAtFixedRate(this::update, delayUntillMidnight, TimeUnit.DAYS.toMinutes(period),
                TimeUnit.MINUTES);
    }

    private long getDelayUntillMidnight() {
        LocalDateTime today = LocalDateTime.now();
        int days = 1;
        LocalDate tomorrow = LocalDate.now().plusDays(days);
        return today.until(tomorrow.atStartOfDay(), ChronoUnit.MINUTES);
    }

    private void update() {
        int days = 1;
        LocalDate yesterday = LocalDate.now().minusDays(days);
        List<Quote> yesterdayQuotes = dailyArchive.getQuotes(yesterday);
        addQuotes(yesterdayQuotes);
        dailyArchive.deleteQuotes(yesterday);
    }

    private void createFile(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException ioException) {
                LOGGER.debug(ioException);
            }
        }
    }

    private void addQuotes(List<Quote> quotes) {
        if (quotes.isEmpty()) {
            throw new IllegalArgumentException("Parameter quotes cannot be empty");
        }
        Quote first = quotes.get(0);
        LocalDate firstDate = first.getDate().toLocalDate();
        Path monthQuotes = getPath(firstDate);
        if (!Files.exists(monthQuotes)) {
            createFile(monthQuotes);
        }
        writeTo(monthQuotes, gson.toJson(firstDate));
        writeTo(monthQuotes, gson.toJson(quotes));
    }

    private Path getPath(LocalDate date) {
        String dateFormat = "yyyy-MM";
        String name = namePattern + date.format(DateTimeFormatter.ofPattern(dateFormat));
        return location.resolve(name);
    }

    private synchronized void writeTo(Path path, String json) {
        try (DataOutputStream dos = new DataOutputStream(Files.newOutputStream(path, APPEND))) {
            byte[] toBeWritten = json.getBytes();
            dos.writeInt(toBeWritten.length);
            dos.write(toBeWritten);

        } catch (IOException ioException) {
            LOGGER.debug(ioException);
        }
    }

    public void addLatestQuote(Quote quote) {
        if (quote == null) {
            throw new IllegalArgumentException("Argument quote cannot be null value");
        }
        dailyArchive.addLatestQuote(quote);
    }

    public List<Quote> getOldQuotes(LocalDate start, LocalDate end) {
        LocalDate today = LocalDate.now();
        if (end.equals(today) || start.equals(today)) {
            throw new IllegalArgumentException("End or start date cannot be today");
        }
        List<Quote> allQuotes = new ArrayList<>();
        LocalDate current = start;
        do {
            Path path = getPath(start);
            List<Quote> quotes = readFrom(path, start, end);
            allQuotes.addAll(quotes);
            current = current.plusDays(1);
        } while (!current.equals(end));
        return allQuotes;
    }

    private synchronized List<Quote> readFrom(Path path, LocalDate start, LocalDate end) {
        List<Quote> quotes = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(Files.newInputStream(path))) {
            int size = 0;
            byte[] array;
            while (dis.available() > 0) {
                size = dis.readInt();
                array = dis.readNBytes(size);
                String jsonDate = new String(array);
                LocalDate quotesDate = gson.fromJson(jsonDate, LocalDate.class);

                size = dis.readInt();
                array = dis.readNBytes(size);
                String jsonQuotes = new String(array);
                if (!quotesDate.isAfter(end) && !quotesDate.isBefore(start)) {
                    Type type = new TypeToken<List<Quote>>() {
                    }.getType();
                    quotes.addAll(gson.fromJson(jsonQuotes, type));
                }
            }

        } catch (IOException ioException) {
            LOGGER.debug(ioException);
        }
        return quotes;
    }

}
