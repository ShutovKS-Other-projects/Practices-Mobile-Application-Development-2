package ru.mirea.shutov.amlcryptocheck.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import ru.mirea.shutov.amlcryptocheck.R;
import ru.mirea.shutov.data.repository.AuthRepositoryImpl;
import ru.mirea.shutov.domain.repository.AuthCallback;
import ru.mirea.shutov.domain.repository.AuthRepository;
import ru.mirea.shutov.domain.usecase.CheckUserLoggedInUseCase;
import ru.mirea.shutov.domain.usecase.RegisterUseCase;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private MaterialButton buttonRegister;
    private LinearLayout layoutLoginLink;

    private RegisterUseCase registerUseCase;
    private CheckUserLoggedInUseCase checkUserLoggedInUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        var authRepository = new AuthRepositoryImpl();

        checkUserLoggedInUseCase = new CheckUserLoggedInUseCase(authRepository);
        if (checkUserLoggedInUseCase.execute()) {
            navigateToMain();
            return;
        }

        registerUseCase = new RegisterUseCase(authRepository);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        layoutLoginLink = findViewById(R.id.layoutLoginLink);

        buttonRegister.setOnClickListener(v -> registerUser());

        layoutLoginLink.setOnClickListener(v -> {
            finish();
        });
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        registerUseCase.execute(email, password, new AuthCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                navigateToMain();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(RegisterActivity.this, "Registration failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateToMain() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}