package com.trading.protrading.service;


import com.trading.protrading.demotesting.RealTimeStrategyTestingTasksStorage;
import com.trading.protrading.dto.RuleDTO;
import com.trading.protrading.dto.StrategyDTO;
import com.trading.protrading.model.Account;
import com.trading.protrading.model.Condition;
import com.trading.protrading.repository.*;
import com.trading.protrading.strategytesting.TestConfiguration;
import com.trading.protrading.exceptions.StrategyNotFoundException;
import com.trading.protrading.model.Rule;
import com.trading.protrading.model.Strategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StrategyService {

    private AccountRepository accountRepository;
    private StrategyRepository strategyRepository;
    private ReportRepository reportRepository;
    private ConditionRepository conditionRepository;
    private RuleRepository ruleRepository;


    private RealTimeStrategyTestingTasksStorage tester;

    public StrategyService(AccountRepository accountRepository, StrategyRepository strategyRepository, ReportRepository reportRepository, ConditionRepository conditionRepository, RuleRepository ruleRepository) {
        this.accountRepository = accountRepository;
        this.strategyRepository = strategyRepository;
        this.reportRepository = reportRepository;
        this.conditionRepository = conditionRepository;
        this.ruleRepository = ruleRepository;
    }

    public void create(StrategyDTO strategy, String username) {
        Optional<Account> account = this.accountRepository.findFirstByUserName(username);
        if (account.isEmpty()) {
            // TODO THROW
        }
        Strategy databaseStrategyModel = new Strategy();

        databaseStrategyModel.setName(strategy.getName());
        databaseStrategyModel.setUser(account.get());
        this.strategyRepository.save(databaseStrategyModel);

        Set<Rule> rules = strategy.getRules()
                .stream()
                .map(this::mapFromDto)
                .collect(Collectors.toSet());
        for (Rule rule : rules) {
            rule.setStrategy(databaseStrategyModel);
        }
        databaseStrategyModel.setRules(rules);

        Set<Condition> conditions = databaseStrategyModel.getRules().stream().map(Rule::getCondition).collect(Collectors.toSet());
        this.conditionRepository.saveAll(conditions);
        this.ruleRepository.saveAll(rules);


        this.strategyRepository.save(databaseStrategyModel);
    }

    public void delete(String userName, String strategyName) throws StrategyNotFoundException {
        Optional<Strategy> strategy = this.strategyRepository.getFirstByNameAndUser_UserName(userName, strategyName);
        if (strategy.isEmpty()) {
            throw new StrategyNotFoundException("Can not delete strategy " + strategyName + " as it doesn't exist for user " + userName);
        }

        this.strategyRepository.delete(strategy.get());
    }

    public Strategy get(String userName, String strategyName) throws StrategyNotFoundException {
        Optional<Strategy> strategy = this.strategyRepository.getFirstByNameAndUser_UserName(userName, strategyName);
        if (strategy.isEmpty()) {
            throw new StrategyNotFoundException("Strategy " + strategyName + "not found for user " + userName);
        }

        return strategy.get();
    }

    public List<Strategy> getAll(String userName) {
        return this.strategyRepository.getAllByUser_UserName(userName);
    }

    public void changeName(String userName, String oldName, String newName) throws StrategyNotFoundException {
        Optional<Strategy> strategy = this.strategyRepository.getFirstByNameAndUser_UserName(userName, oldName);
        if (strategy.isEmpty()) {
            throw new StrategyNotFoundException("Strategy with name " + oldName + " was not found for user " + userName);
        }
        strategy.get().setName(newName);
        this.strategyRepository.save(strategy.get());
    }

    public void changeRules(String userName, String strategyName, Set<RuleDTO> rules) throws StrategyNotFoundException {
        Optional<Strategy> strategy = this.strategyRepository.getFirstByNameAndUser_UserName(strategyName, userName);
        if (strategy.isEmpty()) {
            throw new StrategyNotFoundException("Strategy with name " + strategyName + "for user" + userName + "not found and couldn't be updated");
        }
        Set<Rule> oldRules = strategy.get().getRules();
        Set<Condition> oldConditions = oldRules.stream().map(Rule::getCondition).collect(Collectors.toSet());

        Set<Rule> ruleDatabaseModels = rules.stream().map(this::mapFromDto).collect(Collectors.toSet());
        for (Rule rule : ruleDatabaseModels) {
            rule.setStrategy(strategy.get());
        }

        strategy.get().setRules(ruleDatabaseModels);

        Set<Condition> conditionDatabaseModels = ruleDatabaseModels.stream().map(Rule::getCondition).collect(Collectors.toSet());

        this.conditionRepository.saveAll(conditionDatabaseModels);
        this.ruleRepository.saveAll(ruleDatabaseModels);
        this.ruleRepository.deleteAll(oldRules);
        this.conditionRepository.deleteAll(oldConditions);

        this.strategyRepository.save(strategy.get());
    }

    public UUID enableStrategy(TestConfiguration testConfiguration)
            throws StrategyNotFoundException {
        Strategy strategy;
        try {
            strategy = strategyRepository.getAllByNameAndUser_UserName(testConfiguration.getStrategyName(),
                    testConfiguration.getUsername()).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new StrategyNotFoundException("Strategy with name " + testConfiguration.getStrategyName() + " was not found.", e);
        }
        UUID reportId = UUID.randomUUID();
        tester.enableStrategy(strategy, testConfiguration, reportId, reportRepository);
        return reportId;
    }

    public void disableStrategy(String username, String strategy) throws StrategyNotFoundException {
        tester.disableStrategy(username, strategy);
    }


    private Rule mapFromDto(RuleDTO ruleDTO) {
        Rule r = new Rule();
        r.setStopLoss(ruleDTO.getStopLoss());
        r.setTakeProfit(ruleDTO.getTakeProfit());
        Condition c = new Condition();
        c.setAssetPrice(ruleDTO.getCondition().getAssetPrice());
        c.setPredicate(ruleDTO.getCondition().getPredicateEnum());

        r.setCondition(c);
        return r;
    }
}