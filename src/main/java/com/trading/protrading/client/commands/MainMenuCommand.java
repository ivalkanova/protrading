package com.trading.protrading.client.commands;

import com.trading.protrading.client.ClientInMemoryStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpClient;

public class MainMenuCommand extends Command {
    public MainMenuCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
    }

    @Override
    public void run(String url) throws IOException {
        while(true) {
            writer.printf("Welcome, %s%n", this.storage.getLoggedUser());
            writer.println("Available menus:");
            writer.println("Account");
            writer.println("Strategies");
            writer.println("Reports");
            writer.println("Testing");
            writer.println("Please choose a menu or type \"Back\" to go to the previous menu. ");

            String line = reader.readLine();
            switch (line.toLowerCase().trim()) {
                case "back":
                    return;
                case "account":

                case "strategies":
                case "reports":
                case "testing":
                    new TestingMenuCommand(httpClient, writer, reader, storage).run(url);
            }
        }
    }
}
