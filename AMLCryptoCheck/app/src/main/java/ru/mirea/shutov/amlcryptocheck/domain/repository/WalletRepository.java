package ru.mirea.shutov.amlcryptocheck.domain.repository;

import java.util.List;
import ru.mirea.shutov.amlcryptocheck.domain.model.WalletCheck;

public interface WalletRepository {
    WalletCheck checkWallet(String address);
    List<WalletCheck> getCheckHistory();
}