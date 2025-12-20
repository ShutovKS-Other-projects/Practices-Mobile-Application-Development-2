package ru.mirea.shutov.data.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Index;

@Entity(tableName = "history_table", indices = {@Index(value = {"address", "checkDate"}, unique = true)})
public class WalletCheckDbo {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String address;
    public int riskScore;
    public long checkDate;
    public String currencyIconUrl;
}