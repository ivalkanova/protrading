package com.trading.protrading.model;

import com.trading.protrading.model.report.Report;
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
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "userId")
    private Account user;
    @OneToMany(mappedBy = "strategy", targetEntity = Report.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Report> reports;
    @OneToMany(mappedBy = "strategy", targetEntity = Rule.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Rule> rules;

    public Strategy() {

    }

}
