package com.trading.protrading.model;

import com.trading.protrading.strategytesting.StrategyTestTask;
import com.trading.protrading.data.strategy.Quote;
import com.trading.protrading.model.report.Report;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "strategies")
public class Strategy {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "userId")
    private Account user;
    @OneToMany(mappedBy = "strategy", targetEntity = Report.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Report> reports;
    @OneToMany(mappedBy = "strategy", targetEntity = Rule.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Rule> rules;

    public Strategy() {}

    public Strategy(String name, Set<Rule> rules) {
        this.name = name;
        this.rules = rules;
    }

    public String getName() {
        return name;
    }

    public void execute(Quote quote, StrategyTestTask test){
        for(Rule rule : rules){
            rule.execute(quote, test);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Strategy strategy = (Strategy) o;
        return Objects.equals(id, strategy.id) &&
                Objects.equals(name, strategy.name) &&
                Objects.equals(rules, strategy.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rules);
    }

    @Override
    public String toString() {
        return "Strategy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + user +
                ", rules=" + rules +
                '}';
    }
}