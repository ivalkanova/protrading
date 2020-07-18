package com.trading.protrading.client.commands;

import com.trading.protrading.client.ClientInMemoryStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpClient;
import java.util.HashMap;

import static org.apache.commons.lang3.math.NumberUtils.toInt;

public class AccountCommand extends Command {

    private HashMap<Integer, Command> commands;

    public AccountCommand(HttpClient httpClient, PrintWriter writer, BufferedReader reader,
                          ClientInMemoryStorage storage) {
        super(httpClient, writer, reader, storage);
        commands = new HashMap<>();
        configureCommands(httpClient, writer, reader, storage);
    }

    private void configureCommands(HttpClient httpClient, PrintWriter writer, BufferedReader reader,
                                   ClientInMemoryStorage storage) {
        commands.put(1, new ChangePasswordCommand(httpClient, writer, reader, storage));
        commands.put(2, new DeleteAccountCommand(httpClient, writer, reader, storage));

    }

    @Override
    public void run(String url) throws IOException {
        int option = askForOption();
        runCommand(option, url);
    }

    private int askForOption() throws IOException {
        writer.println("Please choose one of the following options.");
        writer.println("1.Change password");
        writer.println("2.Delete account");
        writer.println("3.Go Back");
        String input;
        input = reader.readLine();
        int back = 3;
        int option = toInt(input, back);
        int maxOption = 3;
        if (option < 1 || option > maxOption) {
            option = back;
        }
        return option;
    }

    private void runCommand(int option, String url) throws IOException {
       Command command= commands.get(option);
       command.run(url);
    }
}
