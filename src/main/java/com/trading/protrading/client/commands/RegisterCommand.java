package com.trading.protrading.client.commands;

import com.google.gson.JsonObject;
import com.trading.protrading.client.ClientInMemoryStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RegisterCommand extends Command {
    private static final String USER_REGISTER_PATH = "/accounts/create";

    public RegisterCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
    }

    @Override
    public void run(String url) throws IOException {
        this.writer.printf("Please enter your username: ");
        String username = this.reader.readLine();

        this.writer.printf("Please enter your password: ");

        String password = this.reader.readLine();

        this.writer.printf("Please repeat your password: ");
        String passwordConfirmation = this.reader.readLine();

        if (password.compareTo(passwordConfirmation) != 0) {
            this.writer.println("Password mismatch");
            return;
        }

        JsonObject userJson = new JsonObject();
        userJson.addProperty("username", username);
        userJson.addProperty("password", password);

        String userAsJsonString = userJson.toString();

        URI resource = URI.create(url + USER_REGISTER_PATH);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(resource)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(userAsJsonString))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            boolean isCreated = Boolean.parseBoolean(response.body());
            if (isCreated) {
                this.storage.setLoggedUser(username);
                writer.println("The account has been created successfully.");
            } else {
                writer.println("An account with the requested username already exists.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
