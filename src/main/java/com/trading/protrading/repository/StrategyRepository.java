package com.trading.protrading.repository;

import com.trading.protrading.model.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Long> {
    LinkedList<Strategy> getAllByNameAndUser_UserName(String strategyName, String username);
}
