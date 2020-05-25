package com.trading.protrading.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "accounts")
public class AccountModel {

    private @Id
    String userName;
    private String password;
    private String salt;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<StrategyModel> strategies;

    public AccountModel() {
    }

    public AccountModel(String userName, String password, String salt) {
        this.userName = userName;
        this.password = password;
        this.salt = salt;
    }
}
