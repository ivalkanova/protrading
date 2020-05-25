package com.trading.protrading.controller;

import com.trading.protrading.model.AccountModel;
import com.trading.protrading.model.report.ExtendedReportModel;
import com.trading.protrading.repository.AccountRepository;
import com.trading.protrading.repository.ReportRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SampleController {

    private AccountRepository accountRepository;
    private ReportRepository<ExtendedReportModel> extendedReportReportRepository;

    public SampleController(AccountRepository accountRepository, ReportRepository<ExtendedReportModel> extendedReportReportRepository) {
        this.accountRepository = accountRepository;
        this.extendedReportReportRepository = extendedReportReportRepository;
    }

    @GetMapping("/accounts")
    List<AccountModel> all() {
        List<AccountModel> accountModels = this.accountRepository.findAll();
        return accountModels;
    }

    @GetMapping("/reports")
    List<ExtendedReportModel> reports() {
        List<ExtendedReportModel> reports = this.extendedReportReportRepository.findAll();
        return reports;
    }

}
