package ru.mirea.shutov.domain.repository;

import java.util.List;

import ru.mirea.shutov.domain.models.WalletCheck;

public interface WalletRepository {
    WalletCheck checkWallet(String address);
    List<WalletCheck> getCheckHistory();
}