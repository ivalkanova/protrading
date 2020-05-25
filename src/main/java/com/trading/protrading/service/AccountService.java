package com.trading.protrading.service;

import com.trading.protrading.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public void create(String username, String password) {
    }

    public void delete(String username, String password) {

    }

    public void login(String username, String password) {

    }

    public void changePassword(String username, String oldPassword, String newPassword) {

    }

}
