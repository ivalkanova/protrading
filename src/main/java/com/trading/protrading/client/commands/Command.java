package com.trading.protrading.client.commands;

import com.trading.protrading.client.ClientInMemoryStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpClient;

public abstract class Command {
    protected final HttpClient httpClient;
    protected final PrintWriter writer;
    protected final BufferedReader reader;
    protected ClientInMemoryStorage storage;

    public Command(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        this.httpClient = httpClient;
        this.writer = writer;
        this.reader = reader;
        this.storage = storage;
    }

    public abstract void run(String url) throws IOException;
}
