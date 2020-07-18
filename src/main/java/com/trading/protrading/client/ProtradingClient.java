package com.trading.protrading.client;

import com.trading.protrading.client.commands.Command;
import com.trading.protrading.client.commands.LoginCommand;
import com.trading.protrading.client.commands.MainMenuCommand;
import com.trading.protrading.client.commands.PreloginMenuCommand;

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
       String url = this.getFullBaseUrl();

        try {
            new PreloginMenuCommand(client, this.writer,this.reader, this.storage).run(url);

            new MainMenuCommand(client, this.writer,this.reader, this.storage).run(url);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
