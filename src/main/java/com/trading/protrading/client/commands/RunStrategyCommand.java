package com.trading.protrading.client.commands;

import com.google.gson.JsonObject;
import com.trading.protrading.client.ClientInMemoryStorage;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RunStrategyCommand extends Command {
    public static final String GET_STRATEGY_PATH = "/strategies/single/";
    public static final String RUN_STRATEGY_PATH = "/strategies/enable/";
    public static final String BACKTESTING_RUN_STRATEGY_PATH = "/backtesting/enable/";

    private final boolean isBacktesting;

    public RunStrategyCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage, boolean isBacktesting) {
        super(httpClient, writer, reader, storage);
        this.isBacktesting = isBacktesting;
    }

    @Override
    public void run(String url) throws IOException {
        writer.printf("Please enter a strategy name: ");
        String strategyName = reader.readLine();

        URI strategyGetResource = URI.create(url + GET_STRATEGY_PATH + strategyName);

        HttpRequest strategyExistsRequest = HttpRequest
                .newBuilder()
                .header("username", this.storage.getLoggedUser())
                .uri(strategyGetResource)
                .GET()
                .build();

        try {
            HttpResponse<Void> strategyResponse = httpClient.send(strategyExistsRequest, HttpResponse.BodyHandlers.discarding());
            if (strategyResponse.statusCode() == HttpStatus.NOT_FOUND.value()) {
                writer.println("Strategy does not exist. Please try again.");
            } else {
                writer.printf("Please enter the start date (yyyy-MM-dd HH:mm): ");
                String startDate = reader.readLine();
                if (!isDateValid(startDate)) {
                    writer.println("The start date you entered was invalid.");
                    return;
                }
                writer.printf("Please enter end date (yyyy-MM-dd HH:mm): ");
                String endDate = reader.readLine();
                if (!isDateValid(endDate)) {
                    writer.println("The end date you entered was invalid.");
                    return;
                }
                writer.printf("Please enter the funds you'd like to run the strategy with: ");
                String fundsUserInput = reader.readLine();
                try {
                    double funds = Double.parseDouble(fundsUserInput);
                    writer.printf("Please enter the transaction buy funds you'd like to run the strategy with: ");
                    double transactionBuyFunds = Double.parseDouble(fundsUserInput);
                    if (funds <= 0 || transactionBuyFunds <= 0) {
                        throw new NumberFormatException("Funds can't be negative.");
                    }
                    writer.printf("Please enter the asset you want to test with (Gold, Silver, Petrol)");
                    String asset = reader.readLine();

                    JsonObject runConfiguration = new JsonObject();
                    runConfiguration.addProperty("asset", asset);
                    runConfiguration.addProperty("start", startDate);
                    runConfiguration.addProperty("end", endDate);
                    runConfiguration.addProperty("funds", funds);
                    runConfiguration.addProperty("transactionBuyFunds", transactionBuyFunds);

                    String runConfigurationString = runConfiguration.toString();

                    URI runStrategy;
                    if (isBacktesting) {
                        runStrategy = URI.create(url + BACKTESTING_RUN_STRATEGY_PATH + strategyName);
                    } else {
                        runStrategy = URI.create(url + RUN_STRATEGY_PATH + strategyName);
                    }

                    HttpRequest runRequest = HttpRequest.newBuilder()
                            .uri(runStrategy)
                            .header("username", this.storage.getLoggedUser())
                            .header("Content-Type", "text/json")
                            .POST(HttpRequest.BodyPublishers.ofString(runConfigurationString))
                            .build();

                    HttpResponse<String> response = this.httpClient.send(runRequest, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() == HttpStatus.BAD_REQUEST.value()) {
                        writer.println("Invalid asset type provided.");
                    } else if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                        writer.println("Strategy not found.");
                    } else if (response.statusCode() == HttpStatus.OK.value()) {
                        writer.println("Successfully started running strategy.");
                        writer.printf("Here is your UUID: %s", response.body());
                    }

                    } catch (NumberFormatException e) {
                    writer.println("You need to enter a valid positive number.");
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isDateValid(String date) {
        // TODO
        return true;
    }
}
