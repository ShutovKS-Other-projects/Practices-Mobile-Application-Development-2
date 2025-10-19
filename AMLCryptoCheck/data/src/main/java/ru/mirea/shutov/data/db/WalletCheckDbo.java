package ru.mirea.shutov.data.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history_table")
public class WalletCheckDbo {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String address;
    public int riskScore;
    public long checkDate;
}