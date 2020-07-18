package com.trading.protrading.client.commands;

import com.trading.protrading.client.ClientInMemoryStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpClient;

public class TestingMenuCommand extends Command {
    public TestingMenuCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
    }

    @Override
    public void run(String url) throws IOException {
        writer.println("Available commands:");
        writer.println("Run-strategy");
        writer.println("Disable-strategy");
        writer.println("Compare-strategies");
        writer.println("Please choose a menu or type \"Back\" to go to the previous menu. ");

        String line = reader.readLine();
        switch (line.toLowerCase().trim()) {
            case "back":
                return;
            case "run-strategy":
                    new RunStrategyCommand(httpClient, writer, reader, storage, true).run(url);
            case "disable-strategy":
                    new DisableStrategyCommand(httpClient, writer, reader, storage).run(url);
                break;
            case "compare-strategies":
        }
    }
}
