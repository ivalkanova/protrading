package com.trading.protrading.exceptions;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String username) {
        super(username);
    }
}
