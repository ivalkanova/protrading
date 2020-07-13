package com.trading.protrading.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String userName;
    private String password;
    private String salt;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Strategy> strategies;

    public Account() {
    }

    public Account(String userName, String password, String salt) {
        this.userName = userName;
        this.password = password;
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(userName, account.userName) &&
                Objects.equals(password, account.password) &&
                Objects.equals(salt, account.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, salt);
    }
}