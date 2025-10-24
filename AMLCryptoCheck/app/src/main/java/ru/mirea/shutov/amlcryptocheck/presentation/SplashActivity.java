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

        AuthRepository authRepository = new AuthRepositoryImpl();
        CheckUserLoggedInUseCase checkUserLoggedInUseCase = new CheckUserLoggedInUseCase(authRepository);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            boolean isLoggedIn = checkUserLoggedInUseCase.execute();
            if (!isLoggedIn) {
                navigateTo(LoginActivity.class);
            } else {
                navigateTo(MainActivity.class);
            }
        }, SPLASH_DELAY);
    }

    private void navigateTo(Class<?> activityClass) {
        Intent intent = new Intent(SplashActivity.this, activityClass);
        startActivity(intent);
        finish();
    }
}