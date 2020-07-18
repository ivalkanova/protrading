package com.trading.protrading.controller;

import com.trading.protrading.data.AccountCredentials;
import com.trading.protrading.data.ChangePassword;
import com.trading.protrading.exceptions.InvalidPasswordException;
import com.trading.protrading.exceptions.UserAlreadyExistsException;
import com.trading.protrading.exceptions.UserDoesntExistException;
import com.trading.protrading.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.security.spec.InvalidKeySpecException;

@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accounts/create")
    public boolean register(@RequestBody AccountCredentials credentials) {
        try {
            this.accountService.create(credentials.getUsername(), credentials.getPassword());
        } catch (InvalidKeySpecException | UserAlreadyExistsException e) {
            return false;
        }
        return true;
    }

    @PutMapping("/accounts")
    public boolean changePassword(@RequestBody ChangePassword credentials) {
        try {
            this.accountService.changePassword(credentials.getUsername(), credentials.getOldPassword(), credentials.getNewPassword());
        } catch (UserDoesntExistException | InvalidKeySpecException | InvalidPasswordException e) {
            return false;
        }
        return true;
    }

    @PostMapping("/accounts/login")
    public boolean login(@RequestBody AccountCredentials credentials) {
        try {
            this.accountService.login(credentials.getUsername(), credentials.getPassword());
        } catch (UserDoesntExistException | InvalidPasswordException e) {
            return false;
        }
        return true;
    }
}
