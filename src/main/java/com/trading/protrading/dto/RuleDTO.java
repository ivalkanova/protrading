package com.trading.protrading.dto;

import lombok.Data;

@Data
public class RuleDTO {
    private ConditionDTO condition;
    private Double stopLoss;
    private Double takeProfit;
}
