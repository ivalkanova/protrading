package com.trading.protrading.model;

import com.trading.protrading.strategytesting.StrategyTestTask;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.data.strategy.QuoteType;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(id, rule.id) &&
                Objects.equals(condition, rule.condition) &&
                Objects.equals(stopLoss, rule.stopLoss) &&
                Objects.equals(takeProfit, rule.takeProfit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, condition, stopLoss, takeProfit);
    }

    private Double stopLoss;
    private Double takeProfit;
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "strategyId")
    private Strategy strategy;

    public Rule() {}

    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", condition=" + condition +
                ", stopLoss=" + stopLoss +
                ", takeProfit=" + takeProfit +
                '}';
    }

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