package com.trading.protrading.client.commands;

import com.google.gson.JsonObject;
import com.trading.protrading.client.ClientInMemoryStorage;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginCommand extends Command {
    private static final String USER_LOGIN_PATH = "/accounts/login";


    public LoginCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
    }

    @Override
    public void run(String url) throws IOException {

        this.writer.printf("Please enter your username: ");
        String username = this.reader.readLine();

        this.writer.printf("Please enter your password: ");

        String password = this.reader.readLine();

        JsonObject credentials = new JsonObject();
        credentials.addProperty("username", username);
        credentials.addProperty("password", password);

        String credentialsJsonString = credentials.toString();

        URI resource = URI.create(url + USER_LOGIN_PATH);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(resource)
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(credentialsJsonString))
                .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HttpStatus.OK.value()) {
                boolean isUserAuthenticated = Boolean.parseBoolean(response.body());
                if (isUserAuthenticated) {
                    this.storage.setLoggedUser(username);
                    this.writer.println("You have successfully logged in.");
                } else {
                    this.writer.println("Incorrect username or password.");
                }
            } else {
                this.writer.printf("Server returned error code %d", response.statusCode());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
