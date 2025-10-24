package ru.mirea.shutov.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface WalletCheckDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WalletCheckDbo walletCheck);

    @Query("SELECT * FROM history_table ORDER BY checkDate DESC")
    LiveData<List<WalletCheckDbo>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WalletCheckDbo> dtoList);
}