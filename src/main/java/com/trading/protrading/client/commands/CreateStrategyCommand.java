package com.trading.protrading.client.commands;

import com.google.gson.Gson;
import com.trading.protrading.client.ClientInMemoryStorage;
import com.trading.protrading.client.models.Condition;
import com.trading.protrading.client.models.Rule;
import com.trading.protrading.client.models.Strategy;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;

public class CreateStrategyCommand extends Command {

    private final static String CREATE_STRATEGY_PATH = "/strategies/create";
    private final static Logger LOGGER = getLogger(ChangePasswordCommand.class);
    private final Gson gson;

    public CreateStrategyCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
        gson = new Gson();
    }

    @Override
    public void run(String url) {
        if (!storage.isUserLoggedIn()) {
            writer.println("You must be logged in in order to make such a change");
            return;
        }
        try {
            Strategy strategy = getStrategy();
            int status = sendRequest(url, strategy);
            if (status == HttpStatus.OK.value()) {
                writer.println("Strategy created successfully");
            } else {
                writer.println("Unsuccessful operation");
            }
        } catch (IOException | InterruptedException ioException) {
            LOGGER.error("Error when creating strategy", ioException);
            writer.println("An error occurred when attempting to create the strategy");
            writer.println("The stategy was not created");
        }

    }

    private Strategy getStrategy() throws IOException {
        String name = getAnswer("Please enter the strategy name");
        List<Rule> rules = new ArrayList<>();
        String answer;

        do {
            writer.println("Please enter the new Rule");
            Rule rule = getRule();
            rules.add(rule);
            answer = getAnswer("Do you wish to add one more rule. If yes please enter y, if no please enter n");
        } while (answer.equals("y") || answer.equals("Y"));

        return new Strategy(name, rules);
    }

    private Rule getRule() throws IOException {
        String stopLoss = getAnswer("Please enter the stop loss value");
        double stopLossValue = Double.parseDouble(stopLoss);
        String takeProfit = getAnswer("Please enter the profit");
        double takeProfitValue = Double.parseDouble(takeProfit);
        Condition condition = getCondition();
        return new Rule(stopLossValue, takeProfitValue, condition);

    }

    private Condition getCondition() throws IOException {
        String assetPrice = getAnswer("Please enter asset price");
        double price = Double.parseDouble(assetPrice.trim());
        String predicate = getAnswer("Please enter predicate");
        return new Condition(price, predicate);
    }

    private String getAnswer(String message) throws IOException {
        writer.println(message);
        return reader.readLine();
    }

    private int sendRequest(String url, Strategy strategy) throws IOException, InterruptedException {
        URI resource = URI.create(url + CREATE_STRATEGY_PATH);

        String body = gson.toJson(strategy);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(resource)
                .setHeader("Content-Type", "application/json")
                .header("username", storage.getLoggedUser())
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }
}
