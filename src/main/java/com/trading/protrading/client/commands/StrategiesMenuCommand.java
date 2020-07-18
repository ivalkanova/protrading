package com.trading.protrading.client.commands;

import com.trading.protrading.client.ClientInMemoryStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpClient;

public class StrategiesMenuCommand extends Command {

    public StrategiesMenuCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
    }

    @Override
    public void run(String url) throws IOException {
        writer.println("Available commands:");
        writer.println("Create-strategy");
        writer.println("Modify-strategy");
        writer.println("Delete-strategies");
        writer.println("Please choose a menu or type \"Back\" to go to the previous menu. ");

        String line = reader.readLine();
        switch (line.toLowerCase().trim()) {
            case "back":
                return;
            case "create-strategy":
                new CreateStrategyCommand(httpClient, writer, reader, storage).run(url);
                break;
            case "modify-strategy":
            case "show-strategies":
                new ShowStrategiesCommand(httpClient, writer, reader, storage).run(url);
                break;
            case "delete-strategy":
                new DeleteStrategyCommand(httpClient, writer, reader, storage).run(url);
                break;
        }
    }
}
