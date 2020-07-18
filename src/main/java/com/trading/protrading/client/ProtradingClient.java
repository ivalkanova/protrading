package com.trading.protrading.client;

import com.trading.protrading.client.commands.Command;
import com.trading.protrading.client.commands.LoginCommand;

import java.io.*;
import java.net.http.HttpClient;

public class ProtradingClient {
    private static final String EXIT_MESSAGE = "exit";
    private final ClientInMemoryStorage storage;
    private String baseUrl;
    private Integer port;

    PrintWriter writer;
    BufferedReader reader;

    public ProtradingClient(String url, Integer port, InputStream in,
                            OutputStream out) {
        this.baseUrl = url;
        this.port = port;

        this.writer = new PrintWriter(new OutputStreamWriter(out), true);
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.storage = new ClientInMemoryStorage();
    }

    public ProtradingClient(String url, Integer port) {
        this(url, port, System.in, System.out);
    }

    public String getFullBaseUrl() {
        return baseUrl + ":" + port.toString();
    }

    public void run() {
        HttpClient client = HttpClient.newHttpClient();
        String line = "";

        try {
            while (!this.storage.isUserLoggedIn()) {



                Command login = new LoginCommand(client, this.writer, this.reader, this.storage);

                login.run(this.getFullBaseUrl());


            }
        } catch (IOException e) {
            e.printStackTrace();
            // this.writer.write("An input/output exception occurred while reading line.\n");
        }
    }
}
