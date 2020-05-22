package com.trading.protrading.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class ExtendedReport extends Report {
    private Double maxDrawdown;
    private Double returnToDrawdown;
    private Integer maxConsecutiveLosses;


}