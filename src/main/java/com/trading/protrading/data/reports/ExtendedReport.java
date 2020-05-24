package com.trading.protrading.data.reports;

import lombok.Data;

import javax.persistence.Entity;
@Entity
@Data
public class ExtendedReport extends Report {
    private Double maxDrawdown;
    private Double returnToDrawdown;
    private Integer maxConsecutiveLosses;


}