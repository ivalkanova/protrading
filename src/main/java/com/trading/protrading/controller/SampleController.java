package com.trading.protrading.controller;

import com.trading.protrading.data.Account;
import com.trading.protrading.data.ExtendedReport;
import com.trading.protrading.data.Report;
import com.trading.protrading.repository.AccountRepository;
import com.trading.protrading.repository.ReportRepository;
import org.aspectj.weaver.ast.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SampleController {
    private AccountRepository accountRepository;
    private ReportRepository<ExtendedReport> extendedReportReportRepository;

    public SampleController(AccountRepository accountRepository, ReportRepository<ExtendedReport> extendedReportReportRepository) {
        this.accountRepository = accountRepository;
        this.extendedReportReportRepository = extendedReportReportRepository;
    }

    @GetMapping("/accounts")
    List<Account> all() {
        List<Account> accounts = this.accountRepository.findAll();
        return accounts;
    }

    @GetMapping("/reports")
    List<ExtendedReport> reports() {
        List<ExtendedReport> reports = this.extendedReportReportRepository.findAll();
        return reports;
    }

}
