package com.trading.protrading.client.commands;

import com.google.gson.JsonObject;
import com.trading.protrading.client.ClientInMemoryStorage;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChangeStrategyNameCommand extends Command {
    private static final String CHANGE_STRATEGY_NAME_PATH = "/strategies/name/";

    public ChangeStrategyNameCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
    }

    @Override
    public void run(String url) throws IOException {
        this.writer.printf("Please enter the strategy name you want to change: ");
        String oldName = this.reader.readLine();
        this.writer.printf("Please enter the strategy name you want to change to: ");
        String newName = this.reader.readLine();

        URI resource = URI.create(url + CHANGE_STRATEGY_NAME_PATH + oldName);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(resource)
                .header("Content-Type", "text/plain")
                .header("username", this.storage.getLoggedUser())
                .PUT(HttpRequest.BodyPublishers.ofString(newName))
                .build();

        try {
            HttpResponse<Void> response = this.httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() == HttpStatus.OK.value()) {
                writer.println("The strategy name was successfully changed.");
            } else if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                writer.println("A strategy with the requested name was not found.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
