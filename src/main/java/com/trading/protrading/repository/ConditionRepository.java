package com.trading.protrading.repository;

import com.trading.protrading.model.ConditionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionRepository extends JpaRepository<ConditionModel, Long> {

}
