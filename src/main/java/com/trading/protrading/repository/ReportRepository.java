package com.trading.protrading.repository;

import com.trading.protrading.model.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findFirstByPublicId(UUID publicId);
    Set<Report> findAllByStrategy_NameAndStrategy_User_UserName(String strategyName, String userName);
}
