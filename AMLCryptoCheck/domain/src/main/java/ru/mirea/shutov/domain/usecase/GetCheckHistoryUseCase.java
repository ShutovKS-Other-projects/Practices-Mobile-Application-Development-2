package ru.mirea.shutov.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.mirea.shutov.domain.models.WalletCheck;
import ru.mirea.shutov.domain.repository.WalletRepository;

public class GetCheckHistoryUseCase {
    private final WalletRepository walletRepository;

    public GetCheckHistoryUseCase(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public LiveData<List<WalletCheck>> execute() {
        return walletRepository.getCheckHistory();
    }
}