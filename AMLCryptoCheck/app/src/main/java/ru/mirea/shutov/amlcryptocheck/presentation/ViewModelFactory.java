package ru.mirea.shutov.amlcryptocheck.presentation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.shutov.data.db.AppDatabase;
import ru.mirea.shutov.data.network.MockNetworkApi;
import ru.mirea.shutov.data.repository.AuthRepositoryImpl;
import ru.mirea.shutov.data.repository.WalletRepositoryImpl;
import ru.mirea.shutov.domain.repository.AuthRepository;
import ru.mirea.shutov.domain.repository.WalletRepository;
import ru.mirea.shutov.domain.usecase.CheckWalletUseCase;
import ru.mirea.shutov.domain.usecase.GetCheckHistoryUseCase;
import ru.mirea.shutov.domain.usecase.LoginUseCase;
import ru.mirea.shutov.domain.usecase.RegisterUseCase;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public ViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            AppDatabase db = AppDatabase.getDatabase(context);
            MockNetworkApi networkApi = new MockNetworkApi();
            WalletRepository walletRepository = new WalletRepositoryImpl(networkApi, db.walletCheckDao());
            CheckWalletUseCase checkWalletUseCase = new CheckWalletUseCase(walletRepository);
            GetCheckHistoryUseCase getCheckHistoryUseCase = new GetCheckHistoryUseCase(walletRepository);
            return (T) new MainViewModel(checkWalletUseCase, getCheckHistoryUseCase);
        }
        else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            AuthRepository authRepository = new AuthRepositoryImpl();
            LoginUseCase loginUseCase = new LoginUseCase(authRepository);
            return (T) new LoginViewModel(loginUseCase);
        }
        else if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            AuthRepository authRepository = new AuthRepositoryImpl();
            RegisterUseCase registerUseCase = new RegisterUseCase(authRepository);
            return (T) new RegisterViewModel(registerUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}