package com.trading.protrading.dto;

import lombok.Data;

import java.util.Set;

@Data
public class StrategyDTO {
    private String name;
    private Set<RuleDTO> rules;
}
