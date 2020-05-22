package com.trading.protrading.repository;

import com.trading.protrading.data.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository<T extends Report> extends JpaRepository<T, Long> {
}
