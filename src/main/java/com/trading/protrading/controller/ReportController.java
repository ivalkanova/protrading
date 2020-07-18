package com.trading.protrading.controller;

import com.trading.protrading.exceptions.ReportNotFoundException;
import com.trading.protrading.model.report.Report;
import com.trading.protrading.repository.ReportRepository;
import com.trading.protrading.service.BacktestingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.UUID;

@RestController
public class ReportController {
    BacktestingService backtestingService;

    ReportController(BacktestingService backtestingService) {
        this.backtestingService = backtestingService;
    }

    @GetMapping("/reports/all/{strategyName}")
    public Collection<Report> getReportsForStrategy(@PathVariable String strategyName, HttpServletRequest request, HttpServletResponse response) {
        String username = StrategyController.getUsernameFromHeader(request);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return this.backtestingService.getReports(username, strategyName);
    }

    @GetMapping("/reports/single/{reportId}")
    public Report getReportsForStrategy(@PathVariable String reportId, HttpServletResponse response) {
        try {
            return this.backtestingService.getReport(UUID.fromString(reportId));
        } catch (ReportNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return null;
    }
}
