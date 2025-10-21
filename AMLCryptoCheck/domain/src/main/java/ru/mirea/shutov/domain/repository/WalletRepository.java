package ru.mirea.shutov.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.mirea.shutov.domain.models.WalletCheck;

public interface WalletRepository {
    WalletCheck checkWallet(String address);
    LiveData<List<WalletCheck>> getCheckHistory();
}