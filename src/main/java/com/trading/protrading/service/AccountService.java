package com.trading.protrading.service;

import com.trading.protrading.exceptions.InvalidPasswordException;
import com.trading.protrading.exceptions.UserAlreadyExistsException;
import com.trading.protrading.exceptions.UserDoesntExistException;
import com.trading.protrading.model.Account;
import com.trading.protrading.repository.AccountRepository;
import com.trading.protrading.service.crypto.PasswordHasher;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository repository;
    private final PasswordHasher hasher;

    public AccountService(AccountRepository repository) throws NoSuchAlgorithmException {
        this.repository = repository;
        this.hasher = new PasswordHasher();
    }

    public void create(String username, String password) throws InvalidKeySpecException, UserAlreadyExistsException {
        if (this.repository.existsByUserName(username)) {
            throw new UserAlreadyExistsException(username);
        }

        byte[] salt = this.hasher.generateSalt();
        byte[] passwordHash =  this.hasher.encode(password, salt);

        Account user = new Account(username, passwordHash, salt);
        this.repository.save(user);
    }



    public void delete(String username, String password) {

    }

    public void login(String username, String password) throws UserDoesntExistException, InvalidPasswordException {
        Optional<Account> user =  this.repository.findFirstByUserName(username);
        if (user.isEmpty()) {
            throw new UserDoesntExistException(username);
        }
        this.validatePassword(user.get(), password);
    }

    public void changePassword(String username, String oldPassword, String newPassword) throws UserDoesntExistException, InvalidPasswordException, InvalidKeySpecException {
        Optional<Account> user = this.repository.findFirstByUserName(username);
        if (user.isEmpty()) {
            throw new UserDoesntExistException(username);
        }
        boolean isPasswordMatching = this.validatePassword(user.get(), oldPassword);
        if (!isPasswordMatching) {
            throw new InvalidPasswordException(user.get().getUserName());
        }
        byte[] salt = this.hasher.generateSalt();
        byte[] passwordHash =  this.hasher.encode(newPassword, salt);

        user.get().setPassword(passwordHash);
        user.get().setSalt(salt);

        this.repository.save(user.get());
    }

    private boolean validatePassword(Account user, String password)  {
        byte[] passwordHash = user.getPassword();
        byte[] salt = user.getSalt();

        boolean isSamePassword;
        try {
            isSamePassword = this.hasher.isMatching(password, passwordHash, salt);
        }
        catch (InvalidKeySpecException e) {
            return false;
        }
        return isSamePassword;
    }

}
