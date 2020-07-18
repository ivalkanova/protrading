package com.trading.protrading.client.commands;

import com.trading.protrading.client.ClientInMemoryStorage;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DisableStrategyCommand extends Command {
    public static final String DISABLE_STRATEGY_PATH = "/strategies/disable/";
    public DisableStrategyCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
    }

    @Override
    public void run(String url) throws IOException {
        writer.printf("Enter a strategy to run: ");
        String strategyName = reader.readLine();

        URI resource = URI.create(url + DISABLE_STRATEGY_PATH + strategyName);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(resource)
                .header("Content-Type", "text/plain")
                .header("username", this.storage.getLoggedUser())
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<Void> response =  httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                writer.println("Strategy was not found.");
            } else if (response.statusCode() == HttpStatus.OK.value()) {
                writer.println("Strategy is successfully disabled.");
            } else {
                writer.println("Something unexpected happened.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
