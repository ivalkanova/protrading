package com.trading.protrading.model;

import com.trading.protrading.model.report.Report;
import com.trading.protrading.model.rule.OpenRule;
import com.trading.protrading.model.rule.Rule;
import lombok.Data;

import javax.persistence.*;
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
    @JoinColumn(nullable = false, name = "userName", referencedColumnName = "userName")
    private Account user;
    @OneToMany(mappedBy = "strategy", targetEntity = Report.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Report> reports;
    @OneToOne(optional = false)
    @JoinColumn(name = "openRuleId", referencedColumnName = "id")
    private OpenRule openRule;
    @OneToOne(optional = false)
    @JoinColumn(name = "closeRuleId", referencedColumnName = "id")
    private Rule closeRule;
    @OneToOne(optional = false)
    @JoinColumn(name = "buyRuleId", referencedColumnName = "id")
    private Rule buyRule;
    @OneToOne(optional = false)
    @JoinColumn(name = "sellRuleId", referencedColumnName = "id")
    private Rule sellRule;

    public Strategy() {

    }

}
