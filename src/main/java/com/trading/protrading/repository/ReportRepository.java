package com.trading.protrading.repository;

import com.trading.protrading.model.report.ReportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository<T extends ReportModel> extends JpaRepository<T, Long> {

}
