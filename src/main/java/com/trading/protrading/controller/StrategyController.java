package com.trading.protrading.controller;

import com.trading.protrading.demotesting.DemoTester;
import com.trading.protrading.dto.StrategyDTO;
import com.trading.protrading.dto.TestConfigurationDTO;
import com.trading.protrading.exceptions.InvalidAssetException;
import com.trading.protrading.exceptions.StrategyAlreadyRunningException;
import com.trading.protrading.exceptions.StrategyNotFoundException;
import com.trading.protrading.model.Strategy;
import com.trading.protrading.service.BacktestingService;
import com.trading.protrading.service.StrategyService;
import com.trading.protrading.strategytesting.TestConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
public class StrategyController {
    public static final String USERNAME_HEADER_KEY = "username";


    private final StrategyService strategyService;

    public StrategyController(StrategyService strategyService) {
        this.strategyService = strategyService;
    }


    @PostMapping("/strategies/create")
    public void create(@RequestBody StrategyDTO strategy, HttpServletRequest request, HttpServletResponse response) {
        String username = getUsernameFromHeader(request);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        this.strategyService.create(strategy, username);



        System.out.println(request.getHeaderNames().nextElement());
    }

    @PutMapping("/strategies/name/{oldName}")
    public void modifyStrategyName(@PathVariable String oldName, @RequestBody String newName, HttpServletRequest request, HttpServletResponse response) {
        String username = getUsernameFromHeader(request);
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
        String username = getUsernameFromHeader(request);
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

    @GetMapping("/strategies/all")
    public List<StrategyDTO> getAllStrategies(HttpServletRequest request, HttpServletResponse response) {
        String username = getUsernameFromHeader(request);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return this.strategyService.getAll(username);
    }

    @GetMapping("/strategies/single/{name}")
    public StrategyDTO getStrategy(@PathVariable String name, HttpServletRequest request, HttpServletResponse response) {
        String username = getUsernameFromHeader(request);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        try {
            return this.strategyService.getOne(username, name);
        } catch (StrategyNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @DeleteMapping("/strategies/{name}")
    public void deleteStrategy(@PathVariable String name, HttpServletRequest request, HttpServletResponse response) {
        String username = getUsernameFromHeader(request);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            this.strategyService.delete(username, name);
        } catch (StrategyNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (StrategyAlreadyRunningException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @PostMapping("/strategies/enable/{name}")
    public UUID enableRealTimeStrategy(@PathVariable String name, @RequestBody TestConfigurationDTO configuration, HttpServletRequest request, HttpServletResponse response) {
        String username = getUsernameFromHeader(request);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        if (!configuration.isValidAsset()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        try {
            TestConfiguration testConfiguration = new TestConfiguration(username, name, configuration.getAssetEnum(), configuration.getStart(),
                    configuration.getEnd(),
                    configuration.getFunds(), configuration.getTransactionBuyFunds());
            return this.strategyService.enableStrategy(testConfiguration);

        } catch (InvalidAssetException | StrategyNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }


    }

    @PostMapping("/strategies/disable/{name}") // ???
    public void disableRealTimeStrategy(@PathVariable String name, HttpServletRequest request, HttpServletResponse response) {
        String username = this.getUsernameFromHeader(request);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            this.strategyService.disableStrategy(username, name);
        }
        catch (StrategyNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    // This can be modified later to token authentication relatively easy.
    // Just extract the username from the token and add an Interceptor that validates the token
    public static String getUsernameFromHeader(HttpServletRequest request) {
        return request.getHeader(USERNAME_HEADER_KEY);
    }
}
