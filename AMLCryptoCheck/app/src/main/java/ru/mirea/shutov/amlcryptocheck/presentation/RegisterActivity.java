package ru.mirea.shutov.amlcryptocheck.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import ru.mirea.shutov.amlcryptocheck.R;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;

    private EditText editTextEmail, editTextPassword;
    private MaterialButton buttonRegister;
    private ProgressBar progressBar;
    private LinearLayout layoutLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ViewModelFactory viewModelFactory = new ViewModelFactory(this);
        registerViewModel = new ViewModelProvider(this, viewModelFactory).get(RegisterViewModel.class);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        progressBar = findViewById(R.id.progressBar);
        layoutLoginLink = findViewById(R.id.layoutLoginLink);

        registerViewModel.getRegisterResult().observe(this, result -> {
            if (result == null) return;

            progressBar.setVisibility(result.getStatus() == RegisterResult.Status.LOADING ? View.VISIBLE : View.GONE);

            if (result.getStatus() == RegisterResult.Status.SUCCESS) {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                navigateToMain();
            } else if (result.getStatus() == RegisterResult.Status.ERROR) {
                Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        });

        buttonRegister.setOnClickListener(v -> registerUser());
        layoutLoginLink.setOnClickListener(v -> finish());
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        registerViewModel.register(email, password);
    }

    private void navigateToMain() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}