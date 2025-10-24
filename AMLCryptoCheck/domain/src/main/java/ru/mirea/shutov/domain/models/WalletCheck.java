package ru.mirea.shutov.domain.models;

import androidx.annotation.Nullable;

public class WalletCheck {
    private final String address;
    private final int riskScore;
    private final long checkDate;
    @Nullable
    private final String currencyIconUrl;

    public WalletCheck(String address, int riskScore, long checkDate, @Nullable String currencyIconUrl) {
        this.address = address;
        this.riskScore = riskScore;
        this.checkDate = checkDate;
        this.currencyIconUrl = currencyIconUrl;
    }

    public String getAddress() {
        return address;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public long getCheckDate() {
        return checkDate;
    }

    @Nullable
    public String getCurrencyIconUrl() {
        return currencyIconUrl;
    }
}