package com.trading.protrading.controller;

import com.trading.protrading.dto.StrategyDTO;
import com.trading.protrading.exceptions.StrategyNotFoundException;
import com.trading.protrading.service.BacktestingService;
import com.trading.protrading.service.StrategyService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class StrategyController {
    public static final String USERNAME_HEADER_KEY = "username";


    private StrategyService strategyService;
    private BacktestingService backtestingService;

    public StrategyController(StrategyService strategyService, BacktestingService backtestingService) {
        this.strategyService = strategyService;
        this.backtestingService = backtestingService;
    }


    @PostMapping("/strategies/create")
    public void create(@RequestBody StrategyDTO strategy, HttpServletRequest request, HttpServletResponse response) {
        String username = this.getUsernameFromHeader(request);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        this.strategyService.create(strategy, username);



        System.out.println(request.getHeaderNames().nextElement());
    }

    @PutMapping("/strategies/name/{oldName}")
    public void modifyStrategyName(@PathVariable String oldName, @RequestBody String newName, HttpServletRequest request, HttpServletResponse response) {
        String username = this.getUsernameFromHeader(request);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            this.strategyService.changeName(username, oldName, newName);
        } catch (StrategyNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    @PutMapping("/strategies/rules/{name}")
    public void modifyStrategyRules(@PathVariable String name, @RequestBody StrategyDTO strategy, HttpServletRequest request, HttpServletResponse response) {
        String username = this.getUsernameFromHeader(request);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            this.strategyService.changeRules(username, name, strategy.getRules());
        } catch (StrategyNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // This can be modified later to token authentication relatively easy.
    // Just extract the username from the token and add an Interceptor that validates the token
    private String getUsernameFromHeader(HttpServletRequest request) {
        return request.getHeader(USERNAME_HEADER_KEY);
    }
}
