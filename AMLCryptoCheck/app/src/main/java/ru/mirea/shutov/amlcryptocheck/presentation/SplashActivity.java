package ru.mirea.shutov.amlcryptocheck.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.shutov.amlcryptocheck.R;
import ru.mirea.shutov.data.repository.AuthRepositoryImpl;
import ru.mirea.shutov.domain.repository.AuthRepository;
import ru.mirea.shutov.domain.usecase.CheckUserLoggedInUseCase;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(this::checkUserStatus, SPLASH_DELAY);
    }

    private void checkUserStatus() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}