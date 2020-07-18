package com.trading.protrading.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trading.protrading.data.strategy.Asset;
import com.trading.protrading.exceptions.InvalidAssetException;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestConfigurationDTO {
    private String asset;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    private double funds;
    private double transactionBuyFunds;

    @JsonIgnore
    public boolean isValidAsset() {
        return true;
        //return Arrays. this.asset.
    }

    @JsonIgnore
    public Asset getAssetEnum() throws InvalidAssetException {
        switch (this.asset.toLowerCase()) {
            case "gold":
                return Asset.GOLD;
            case "silver":
                return Asset.SILVER;
            case "petrol":
                return Asset.PETROL;
        }
        throw new InvalidAssetException("Invalid asset type " + this.asset);
    }
}
