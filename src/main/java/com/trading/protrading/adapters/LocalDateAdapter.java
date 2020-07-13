package com.trading.protrading.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    private final static String FORMAT = "yyyy-MM-dd";

    @Override
    public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
        String date = localDate.format(DateTimeFormatter.ofPattern(FORMAT));
        jsonWriter.value(date);
    }

    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {
        String date = jsonReader.nextString();
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(FORMAT));
    }
}
