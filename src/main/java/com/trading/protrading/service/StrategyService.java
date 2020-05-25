package com.trading.protrading.service;

import com.trading.protrading.data.strategy.Rule;
import com.trading.protrading.data.strategy.Strategy;
import com.trading.protrading.repository.StrategyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategyService {

    private StrategyRepository repository;

    public StrategyService(StrategyRepository repository) {
        this.repository = repository;
    }

    public void create(Strategy strategy) {

    }

    public void delete(String userName, String strategyName) {

    }

    public Strategy get(String userName, String strategyName) {
        return null;
    }

    public List<Strategy> getAll(String userName) {
        return null;
    }

    public void changeName(String userName, String oldName, String newName) {

    }

    public void changeRules(String userName, String strategyName, List<Rule> rules) {

    }

    public void changeTransactionBuyFunds(String userName, String strategyName, double transactionBuyFunds) {

    }

    public void changeTransactionSellAmount(String userName, String strategyName, int transactionSellAmount) {

    }

}
