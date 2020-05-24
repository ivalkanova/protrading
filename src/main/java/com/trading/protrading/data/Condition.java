package com.trading.protrading.data;

import com.trading.protrading.data.rules.Rule;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "conditions")
public class Condition {
    @Id @GeneratedValue private Long id;

    private double assetPrice;
    private String predicate;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "ruleId")
    private Rule rule;
}
