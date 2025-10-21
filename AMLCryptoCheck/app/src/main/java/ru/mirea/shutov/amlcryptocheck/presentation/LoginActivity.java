package ru.mirea.shutov.amlcryptocheck.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import ru.mirea.shutov.amlcryptocheck.R;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private EditText editTextEmail, editTextPassword;
    private MaterialButton buttonSignIn;
    private ProgressBar progressBar;
    private TextView textViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ViewModelFactory viewModelFactory = new ViewModelFactory(this);
        loginViewModel = new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        progressBar = findViewById(R.id.progressBar);
        textViewRegister = findViewById(R.id.textViewRegister);

        loginViewModel.getLoginResult().observe(this, result -> {
            if (result == null) return;

            progressBar.setVisibility(result.getStatus() == LoginResult.Status.LOADING ? View.VISIBLE : View.GONE);

            if (result.getStatus() == LoginResult.Status.SUCCESS) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                navigateToMain();
            } else if (result.getStatus() == LoginResult.Status.ERROR) {
                Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        });

        buttonSignIn.setOnClickListener(v ->
                loginUser());

        textViewRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        loginViewModel.login(email, password);
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}