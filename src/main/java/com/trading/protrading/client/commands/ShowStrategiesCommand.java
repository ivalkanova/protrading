package com.trading.protrading.client.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trading.protrading.client.ClientInMemoryStorage;
import com.trading.protrading.client.models.Rule;
import com.trading.protrading.client.models.Strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class ShowStrategiesCommand extends Command {
    public static final String SHOW_STRATEGIES_PATH = "/strategies/all";

    public ShowStrategiesCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
    }

    @Override
    public void run(String url) throws IOException {
        URI resource = URI.create(url + SHOW_STRATEGIES_PATH);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(resource)
                .header("username", this.storage.getLoggedUser())
                .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Type strategyListType = new TypeToken<ArrayList<Strategy>>() {}.getType();

            Gson gson = new Gson();
            List<Strategy> strategies = gson.fromJson(response.body(), strategyListType);

            for (Strategy strategy : strategies) {
                writer.println(strategy.toString());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
