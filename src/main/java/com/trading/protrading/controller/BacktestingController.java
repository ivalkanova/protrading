package com.trading.protrading.controller;

import com.trading.protrading.dto.TestConfigurationDTO;
import com.trading.protrading.exceptions.InvalidAssetException;
import com.trading.protrading.exceptions.StrategyNotFoundException;
import com.trading.protrading.service.BacktestingService;
import com.trading.protrading.strategytesting.TestConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
public class BacktestingController {
    private BacktestingService backtestingService;

    public BacktestingController(BacktestingService backtestingService) {
        this.backtestingService = backtestingService;
    }

    @PostMapping("/backtesting/enable/{strategyName}")
    public UUID enableStrategyOnOldData(@PathVariable String strategyName, TestConfigurationDTO configuration, HttpServletRequest request, HttpServletResponse response) {
        String username = StrategyController.getUsernameFromHeader(request);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        if (!configuration.isValidAsset()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        try {
            TestConfiguration testConfiguration = new TestConfiguration(username, strategyName, configuration.getAssetEnum(), configuration.getStart(),
                    configuration.getEnd(),
                    configuration.getFunds(), configuration.getTransactionBuyFunds());
            return this.backtestingService.enableStrategy(testConfiguration);

        } catch (InvalidAssetException | StrategyNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

    }


}
