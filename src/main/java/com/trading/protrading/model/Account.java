package com.trading.protrading.model;

import lombok.Data;

import javax.persistence.*;
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
}