package ru.mirea.shutov.amlcryptocheck.domain.usecase;

import java.util.List;
import ru.mirea.shutov.amlcryptocheck.domain.model.WalletCheck;
import ru.mirea.shutov.amlcryptocheck.domain.repository.WalletRepository;

public class GetCheckHistoryUseCase {
    private final WalletRepository walletRepository;

    public GetCheckHistoryUseCase(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<WalletCheck> execute() {
        return walletRepository.getCheckHistory();
    }
}