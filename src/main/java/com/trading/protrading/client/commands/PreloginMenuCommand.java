package com.trading.protrading.client.commands;

import com.trading.protrading.client.ClientInMemoryStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpClient;

public class PreloginMenuCommand extends Command {
    public PreloginMenuCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader, ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
    }

    @Override
    public void run(String url) throws IOException {
        while (!this.storage.isUserLoggedIn()) {
            writer.println("Welcome to the Proclient Application!");
            writer.println("To continue, please select one of the following commands");
            writer.println("Login");
            writer.println("Register");

            writer.printf("Choose a command: ");
            String line = this.reader.readLine().toLowerCase();

            Command command = null;
            if (line.equals("login")) {
                command = new LoginCommand(this.httpClient, this.writer, this.reader, this.storage);
            } else if (line.equals("register")) {
                command = new RegisterCommand(this.httpClient, this.writer, this.reader, this.storage);
            }
            if (command != null) {
                command.run(url);
            }

        }
    }
}
