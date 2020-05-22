package com.trading.protrading.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Strategy {
    @Id @GeneratedValue private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(nullable = false, name = "userName" , referencedColumnName = "userName")
    private Account user;

    @OneToMany(mappedBy = "strategy", targetEntity = Report.class, fetch = FetchType.LAZY)
    private Set<Report> reports;

    public Strategy() {

    }


}
