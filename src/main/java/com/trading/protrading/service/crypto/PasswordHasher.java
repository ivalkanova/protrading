package com.trading.protrading.service.crypto;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class PasswordHasher {
    private static final Integer DEFAULT_LENGTH = 16;
    static private final String HASHING_ALGORITHM = "PBKDF2WithHmacSHA1";

    private final SecretKeyFactory keyFactory;
    private final SecureRandom random;

    public PasswordHasher(SecretKeyFactory keyFactory) {
        this.keyFactory = keyFactory;
        this.random = new SecureRandom();
    }

    public PasswordHasher() throws NoSuchAlgorithmException {
        this.keyFactory = SecretKeyFactory.getInstance(HASHING_ALGORITHM);
        this.random = new SecureRandom();
    }

    public byte[] generateSalt(Integer length) {
        byte[] salt = new byte[length];
        this.random.nextBytes(salt);

        return salt;
    }
    public byte[] generateSalt() {
        return this.generateSalt(DEFAULT_LENGTH);
    }


    public boolean isMatching(String password, byte[] hashedPassword, byte[] salt) throws InvalidKeySpecException {
        byte[] hashInputPassword = this.encode(password, salt);
        return Arrays.equals(hashedPassword, hashInputPassword);
    }

    public byte[] encode(String password, byte[] salt) throws InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);

        return this.keyFactory.generateSecret(spec).getEncoded();
    }

}
