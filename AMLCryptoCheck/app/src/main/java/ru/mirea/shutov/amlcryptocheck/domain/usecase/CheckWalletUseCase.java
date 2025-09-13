package ru.mirea.shutov.amlcryptocheck.domain.usecase;

import ru.mirea.shutov.amlcryptocheck.domain.model.WalletCheck;
import ru.mirea.shutov.amlcryptocheck.domain.repository.WalletRepository;

public class CheckWalletUseCase {
    private final WalletRepository walletRepository;
    public CheckWalletUseCase(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }
    public WalletCheck execute(String address) {
        return walletRepository.checkWallet(address);
    }
}