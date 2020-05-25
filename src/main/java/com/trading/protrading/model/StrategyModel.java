package com.trading.protrading.model;

import com.trading.protrading.model.report.ReportModel;
import com.trading.protrading.model.rule.OpenRuleModel;
import com.trading.protrading.model.rule.RuleModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "strategies")
public class StrategyModel {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(nullable = false, name = "userName", referencedColumnName = "userName")
    private AccountModel user;
    @OneToMany(mappedBy = "strategyModel", targetEntity = ReportModel.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ReportModel> reportModels;
    @OneToOne(optional = false)
    @JoinColumn(name = "openRuleId", referencedColumnName = "id")
    private OpenRuleModel openRuleModel;
    @OneToOne(optional = false)
    @JoinColumn(name = "closeRuleId", referencedColumnName = "id")
    private RuleModel closeRuleModel;
    @OneToOne(optional = false)
    @JoinColumn(name = "buyRuleId", referencedColumnName = "id")
    private RuleModel buyRuleModel;
    @OneToOne(optional = false)
    @JoinColumn(name = "sellRuleId", referencedColumnName = "id")
    private RuleModel sellRuleModel;

    public StrategyModel() {

    }

}
