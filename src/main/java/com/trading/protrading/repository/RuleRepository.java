package com.trading.protrading.repository;

import com.trading.protrading.model.rule.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository<T extends Rule> extends JpaRepository<T, Long> {

}
