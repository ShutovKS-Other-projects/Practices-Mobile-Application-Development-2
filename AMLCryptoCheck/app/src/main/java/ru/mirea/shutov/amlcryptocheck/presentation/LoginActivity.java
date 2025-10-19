package ru.mirea.shutov.amlcryptocheck.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import ru.mirea.shutov.amlcryptocheck.R;
import ru.mirea.shutov.data.repository.AuthRepositoryImpl;
import ru.mirea.shutov.domain.repository.AuthCallback;
import ru.mirea.shutov.domain.repository.AuthRepository;
import ru.mirea.shutov.domain.usecase.CheckUserLoggedInUseCase;
import ru.mirea.shutov.domain.usecase.LoginUseCase;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private MaterialButton buttonSignIn;
    private TextView textViewRegister;

    private LoginUseCase loginUseCase;
    private CheckUserLoggedInUseCase checkUserLoggedInUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var authRepository = new AuthRepositoryImpl();

        checkUserLoggedInUseCase = new CheckUserLoggedInUseCase(authRepository);
        if (checkUserLoggedInUseCase.execute()) {
            navigateToMain();
            return;
        }

        loginUseCase = new LoginUseCase(authRepository);

        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        textViewRegister = findViewById(R.id.textViewRegister);

        buttonSignIn.setOnClickListener(v -> loginUser());
        textViewRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void loginUser() {
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

        loginUseCase.execute(email, password, new AuthCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                navigateToMain();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(LoginActivity.this, "Authentication failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}