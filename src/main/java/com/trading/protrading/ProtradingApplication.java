package com.trading.protrading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProtradingApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ProtradingApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
