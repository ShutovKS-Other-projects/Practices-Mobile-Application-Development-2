package ru.mirea.shutov.data.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletCheckDto {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("riskScore")
    @Expose
    private Integer riskScore;

    @SerializedName("checkDate")
    @Expose
    private Long checkDate;

    @SerializedName("currencyIconUrl")
    @Expose
    private String currencyIconUrl;

    public WalletCheckDto() {
    }

    public WalletCheckDto(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public Long getCheckDate() {
        return checkDate;
    }

    public String getCurrencyIconUrl() {
        return currencyIconUrl;
    }
}