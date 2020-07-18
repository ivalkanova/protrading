package com.trading.protrading.repository;

import com.trading.protrading.model.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Long> {
    LinkedList<Strategy> getAllByNameAndUser_UserName(String strategyName, String username);

    Optional<Strategy> getFirstByNameAndUser_UserName(String strategyName, String username);

    List<Strategy> getAllByUser_UserName(String username);
}
