package com.trading.protrading.model;

import com.trading.protrading.strategytesting.StrategyTestTask;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.data.strategy.QuoteType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rules")
public class Rule {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "id", name = "conditionId")
    private Condition condition;
    private Double stopLoss;
    private Double takeProfit;
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "strategyId")
    private Strategy strategy;

    public Rule(Condition condition, Double stopLoss, Double takeProfit) {
        this.condition = condition;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
    }

    public void execute(Quote quote, StrategyTestTask test) {
        QuoteType quoteType = quote.getType();
        double currentPrice = quote.getPrice();

        if (quoteType == QuoteType.SELL && test.tradeIsOpen()) {
            double openingPrice = test.getTradeOpeningPrice();
            double profit = currentPrice - openingPrice;
            if (profit > takeProfit || profit < -stopLoss) {
                test.closeTrade(currentPrice);
            }
        } else if (quoteType == QuoteType.BUY && !test.tradeIsOpen()) {
            if(condition.checkPredicate(currentPrice)){
                test.openTrade(quote, stopLoss);
            }
        }
    }
}