package com.trading.protrading.client;

public class ProtradingClientApplication {
    private static String DEFAULT_URL = "http://localhost";
    private static Integer DEFAULT_PORT = 8080;


    public static void main(String[] args) {
        ProtradingClient app = new ProtradingClient(DEFAULT_URL, DEFAULT_PORT);

        app.run();
    }
}
