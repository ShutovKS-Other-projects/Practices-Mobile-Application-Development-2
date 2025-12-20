package ru.mirea.shutov.data.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {WalletCheckDbo.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WalletCheckDao walletCheckDao();

    private static volatile AppDatabase INSTANCE;

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DELETE FROM history_table WHERE id NOT IN (SELECT MAX(id) FROM history_table GROUP BY address, checkDate)");
            database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_history_address_checkDate ON history_table(address, checkDate)");
        }
    };

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}