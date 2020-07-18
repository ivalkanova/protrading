package com.trading.protrading.exceptions;

public class UserDoesntExistException extends Exception {


    public UserDoesntExistException(String username) {
        super(username);
    }
}
