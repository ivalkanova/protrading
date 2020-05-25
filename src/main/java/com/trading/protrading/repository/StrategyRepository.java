package com.trading.protrading.repository;

import com.trading.protrading.model.StrategyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRepository extends JpaRepository<StrategyModel, Long> {

}
