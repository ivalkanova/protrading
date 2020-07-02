package com.trading.protrading.controller;

import com.trading.protrading.model.Account;
import com.trading.protrading.model.report.Report;
import com.trading.protrading.repository.AccountRepository;
import com.trading.protrading.repository.ReportRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SampleController {

    private AccountRepository accountRepository;
    private ReportRepository reportRepository;

    public SampleController(AccountRepository accountRepository, ReportRepository reportRepository) {
        this.accountRepository = accountRepository;
        this.reportRepository = reportRepository;
    }

    @GetMapping("/accounts")
    List<Account> all() {
        List<Account> accounts = this.accountRepository.findAll();
        return accounts;
    }

    @GetMapping("/reports")
    List<Report> reports() {
        List<Report> reports = this.reportRepository.findAll();
        return reports;
    }

}
