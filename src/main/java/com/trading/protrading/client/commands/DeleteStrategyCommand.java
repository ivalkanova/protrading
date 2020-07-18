package com.trading.protrading.client.commands;

import com.trading.protrading.client.ClientInMemoryStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DeleteStrategyCommand extends Command {
    private static final String DELETE_STRATEGY_PATH = "/strategies/";

    public DeleteStrategyCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
    }

    @Override
    public void run(String url) throws IOException {
        this.writer.printf("Please enter the strategy name you wish to delete: ");
        String strategyName = this.reader.readLine();

        URI resource = URI.create(url + DELETE_STRATEGY_PATH + strategyName);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(resource)
                .header("Content-Type", "application/json")
                .header("username", this.storage.getLoggedUser())
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            boolean isStrategyDeleted = Boolean.parseBoolean(response.body());
            if (isStrategyDeleted) {
                writer.println("The strategy was deleted.");
            } else {
                writer.println("Unsuccessful operation. No strategy with the provided name was found.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
