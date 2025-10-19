package ru.mirea.shutov.amlcryptocheck.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import ru.mirea.shutov.amlcryptocheck.R;
import ru.mirea.shutov.data.db.AppDatabase;
import ru.mirea.shutov.data.network.MockNetworkApi;
import ru.mirea.shutov.data.repository.AuthRepositoryImpl;
import ru.mirea.shutov.data.repository.WalletRepositoryImpl;
import ru.mirea.shutov.domain.models.WalletCheck;
import ru.mirea.shutov.domain.repository.WalletRepository;
import ru.mirea.shutov.domain.usecase.CheckUserLoggedInUseCase;
import ru.mirea.shutov.domain.usecase.CheckWalletUseCase;
import ru.mirea.shutov.domain.usecase.GetCheckHistoryUseCase;

public class MainActivity extends AppCompatActivity {
    // UI элементы общие
    private EditText editTextWalletAddress;
    private MaterialButton buttonCheckRisk;

    // UI элементы гостя
    private TextView textViewLoginLink;

    // UI элементы авторизованного пользователя
    private RecyclerView recyclerViewHistory;
    private HistoryAdapter historyAdapter;
    private GetCheckHistoryUseCase getCheckHistoryUseCase;
    private CheckWalletUseCase checkWalletUseCase;

    private CheckUserLoggedInUseCase checkUserLoggedInUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();

        if (checkUserLoggedInUseCase.execute()) {
            setContentView(R.layout.activity_main_authenticated);
            setupAuthenticatedUI();
            loadHistory();
        } else {
            setContentView(R.layout.activity_main);
            setupGuestUI();
        }
    }

    private void initDependencies() {
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        WalletRepository walletRepository = new WalletRepositoryImpl(
                new MockNetworkApi(),
                db.walletCheckDao()
        );
        checkWalletUseCase = new CheckWalletUseCase(walletRepository);
        getCheckHistoryUseCase = new GetCheckHistoryUseCase(walletRepository);

        var authRepository = new AuthRepositoryImpl();
        checkUserLoggedInUseCase = new CheckUserLoggedInUseCase(authRepository);
    }

    private void setupGuestUI() {
        editTextWalletAddress = findViewById(R.id.editTextWalletAddress);
        buttonCheckRisk = findViewById(R.id.buttonCheckRisk);
        textViewLoginLink = findViewById(R.id.textViewLoginLink);

        buttonCheckRisk.setOnClickListener(v -> performRiskCheck());
        textViewLoginLink.setOnClickListener(v -> navigateToLogin());
    }

    private void setupAuthenticatedUI() {
        editTextWalletAddress = findViewById(R.id.editTextWalletAddress);
        buttonCheckRisk = findViewById(R.id.buttonCheckRisk);

        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        historyAdapter = new HistoryAdapter(this);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(historyAdapter);

        findViewById(R.id.buttonCheckRisk).setOnClickListener(v -> performRiskCheck());
        findViewById(R.id.buttonScan).setOnClickListener(v -> Toast.makeText(this, "Scan feature coming soon!", Toast.LENGTH_SHORT).show());
    }

    private void loadHistory() {
        new Thread(() -> {
            List<WalletCheck> history = getCheckHistoryUseCase.execute();
            runOnUiThread(() -> historyAdapter.setHistoryList(history));
        }).start();
    }

    private void performRiskCheck() {
        String walletAddress = editTextWalletAddress.getText().toString().trim();

        if (TextUtils.isEmpty(walletAddress)) {
            Toast.makeText(this, "Please enter a wallet address", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            WalletCheck result = checkWalletUseCase.execute(walletAddress);

            runOnUiThread(() -> {
                String resultMessage = "Wallet: " + result.getAddress() + "\nRisk Score: " + result.getRiskScore() + "%";
                Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();

                if (checkUserLoggedInUseCase.execute()) {
                    loadHistory();
                }
            });
        }).start();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}