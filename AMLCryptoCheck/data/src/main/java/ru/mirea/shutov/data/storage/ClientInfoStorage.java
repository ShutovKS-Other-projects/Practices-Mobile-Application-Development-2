package ru.mirea.shutov.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class ClientInfoStorage {
    private static final String PREFS_NAME = "client_info_prefs";
    private static final String KEY_USER_EMAIL = "user_email";
    private final SharedPreferences sharedPreferences;

    public ClientInfoStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserEmail(String email) {
        sharedPreferences.edit().putString(KEY_USER_EMAIL, email).apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, "Guest");
    }
}