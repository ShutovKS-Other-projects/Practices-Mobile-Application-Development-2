package ru.mirea.shutov.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface WalletCheckDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WalletCheckDbo walletCheck);

    @Query("SELECT * FROM history_table ORDER BY checkDate DESC")
    LiveData<List<WalletCheckDbo>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<WalletCheckDbo> dtoList);

    @Query("DELETE FROM history_table WHERE id NOT IN (SELECT MAX(id) FROM history_table GROUP BY address, checkDate)")
    void deleteDuplicateKeepingLatest();
}