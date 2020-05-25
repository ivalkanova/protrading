package com.trading.protrading.model.report;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class ExtendedReportModel extends ReportModel {

    private Double maxDrawdown;
    private Double returnToDrawdown;
    private Integer maxConsecutiveLosses;

}