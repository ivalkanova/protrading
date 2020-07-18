package com.trading.protrading.client;

public class ClientInMemoryStorage {
    private String loggedUser;

    public ClientInMemoryStorage() {
        this.loggedUser = null;
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean isUserLoggedIn() {
        return this.loggedUser != null;
    }
}
