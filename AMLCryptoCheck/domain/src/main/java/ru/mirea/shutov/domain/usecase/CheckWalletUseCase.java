package ru.mirea.shutov.domain.usecase;

import ru.mirea.shutov.domain.models.WalletCheck;
import ru.mirea.shutov.domain.repository.WalletRepository;

public class CheckWalletUseCase {
    private final WalletRepository walletRepository;
    public CheckWalletUseCase(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }
    public WalletCheck execute(String address) {
        return walletRepository.checkWallet(address);
    }
}