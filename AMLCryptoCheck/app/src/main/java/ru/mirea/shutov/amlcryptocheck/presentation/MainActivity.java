package ru.mirea.shutov.amlcryptocheck.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.mirea.shutov.amlcryptocheck.R;
import ru.mirea.shutov.data.repository.AuthRepositoryImpl;
import ru.mirea.shutov.domain.repository.AuthRepository;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private AuthRepository authRepository;
    private HistoryAdapter historyAdapter;
    private EditText editTextWalletAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelFactory viewModelFactory = new ViewModelFactory(this);
        mainViewModel = new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);
        authRepository = new AuthRepositoryImpl();

        if (authRepository.isUserLoggedIn()) {
            setContentView(R.layout.activity_main_authenticated);
            setupAuthenticatedUI();
        } else {
            setContentView(R.layout.activity_main);
            setupGuestUI();
        }
    }

    private void setupAuthenticatedUI() {
        editTextWalletAddress = findViewById(R.id.editTextWalletAddress);

        RecyclerView recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        historyAdapter = new HistoryAdapter(this);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(historyAdapter);

        mainViewModel.getHistoryToDisplay().observe(this, historyList ->
                historyAdapter.setHistoryList(historyList));

        findViewById(R.id.buttonCheckRisk).setOnClickListener(v -> {
            String address = editTextWalletAddress.getText().toString().trim();
            if (!address.isEmpty()) {
                mainViewModel.onCheckRiskClicked(address);
                editTextWalletAddress.setText("");
            }
        });

        findViewById(R.id.buttonScan).setOnClickListener(v ->
                Toast.makeText(this, "Scan feature coming soon!", Toast.LENGTH_SHORT).show());
    }

    private void setupGuestUI() {
        editTextWalletAddress = findViewById(R.id.editTextWalletAddress);

        findViewById(R.id.buttonCheckRisk).setOnClickListener(v ->
                mainViewModel.onCheckRiskClicked(editTextWalletAddress.getText().toString()));

        findViewById(R.id.textViewLoginLink).setOnClickListener(v ->
                navigateToLogin());
    }

    private void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}