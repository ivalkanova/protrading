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

public class ChangePasswordCommand extends Command {

    private final static String CHANGE_PASSWORD_PATH = "/accounts";
    private final static Logger LOGGER = getLogger(ChangePasswordCommand.class);
    private String oldPassword;
    private String newPassword;
    private String confirmation;

    public ChangePasswordCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
    }

    @Override
    public void run(String url) {
        if (!storage.isUserLoggedIn()) {
            writer.println("You must be logged in in order to make such a change");
            return;
        }
        try {
            getPasswords();
            if (!newPassword.equals(confirmation)) {
                writer.println("The new password does not match confirmation");
                writer.println("The change was unsuccessful");
                return;

            }
            int status = sendRequest(url);
            if (status == HttpStatus.OK.value()) {
                writer.println("Password Changed successfully");
            } else {
                writer.println("The change was unsuccessful");
            }
        } catch (IOException | InterruptedException exception) {
            writer.println("A problem occured. Please try again later");
            LOGGER.error("error when changing passwords", exception);
        }

    }

    private void getPasswords() throws IOException {
        oldPassword = getAnswer("Please enter your old password: ");
        newPassword = getAnswer("Please enter your new password: ");
        confirmation = getAnswer("Please enter your new password again in order to confirm it");

    }

    private String getAnswer(String message) throws IOException {
        writer.println(message);
        return reader.readLine();
    }

    private int sendRequest(String url) throws IOException, InterruptedException {
        URI resource = URI.create(url + CHANGE_PASSWORD_PATH);

        JsonObject passwords = new JsonObject();
        passwords.addProperty("username", storage.getLoggedUser());
        passwords.addProperty("oldPassword", oldPassword);
        passwords.addProperty("newPassword", newPassword);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(resource)
                .setHeader("Content-Type", "application/json")
                .header("username", storage.getLoggedUser())
                .PUT(HttpRequest.BodyPublishers.ofString(passwords.toString()))
                .build();

        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

}
