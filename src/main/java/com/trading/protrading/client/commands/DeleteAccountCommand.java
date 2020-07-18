package com.trading.protrading.client.commands;

import com.google.gson.JsonObject;
import com.trading.protrading.client.ClientInMemoryStorage;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.apache.logging.log4j.LogManager.getLogger;

public class DeleteAccountCommand extends Command {

    private final static String DELETE_ACCOUNT_PATH = "/accounts";
    private final static Logger LOGGER = getLogger(ChangePasswordCommand.class);
    private String password;
    private String username;

    public DeleteAccountCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
    }

    @Override
    public void run(String url) {
        if (!storage.isUserLoggedIn()) {
            writer.println("You must be logged in in order to make such a change");
            return;
        }
        try {
            getCredential();
            if (!storage.getLoggedUser().equals(username)) {
                writer.println("The username provided does not match the logged user");
            }
            int status = sendRequest(url);
            if (status == HttpStatus.OK.value()) {
                writer.println("Account successfully deleted");
            } else {
                writer.println("The deletion was unsuccessful");
            }
        } catch (IOException | InterruptedException exception) {
            writer.println("A problem occured. Please try again later");
            LOGGER.error("error when deleting account", exception);
        }
    }

    private void getCredential() throws IOException {
        username = getAnswer("Please enter your username: ");
        password = getAnswer("Please enter your password: ");
    }

    private String getAnswer(String message) throws IOException {
        writer.println(message);
        return reader.readLine();
    }

    private int sendRequest(String url) throws IOException, InterruptedException {
        URI resource = URI.create(url + DELETE_ACCOUNT_PATH);

        JsonObject credentials = new JsonObject();
        credentials.addProperty("password", password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(resource)
                .setHeader("Content-Type", "application/json")
                .header("username", storage.getLoggedUser())
                .method("DELETE", HttpRequest.BodyPublishers.ofString(credentials.toString()))
                .build();

        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }
}
