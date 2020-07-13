package com.trading.protrading.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

    private final static String FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        String date = localDateTime.format(DateTimeFormatter.ofPattern(FORMAT));
        jsonWriter.value(date);
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        String date = jsonReader.nextString();
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(FORMAT));
    }

}
