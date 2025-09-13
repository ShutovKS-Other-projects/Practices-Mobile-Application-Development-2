package ru.mirea.shutov.amlcryptocheck.domain.model;

public class WalletCheck {
    private final String address;
    private final int riskScore;
    private final long checkDate;

    public WalletCheck(String address, int riskScore, long checkDate) {
        this.address = address;
        this.riskScore = riskScore;
        this.checkDate = checkDate;
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
}