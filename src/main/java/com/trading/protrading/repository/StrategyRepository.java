package com.trading.protrading.repository;

import com.trading.protrading.model.StrategyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface StrategyRepository extends JpaRepository<StrategyModel, Long> {
    LinkedList<StrategyModel> getAllByNameAndUser_UserName(String strategyName, String username);
}
