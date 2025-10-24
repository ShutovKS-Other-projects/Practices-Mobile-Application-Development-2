package ru.mirea.shutov.domain.network;

import java.util.List;

import ru.mirea.shutov.domain.models.WalletCheck;

public interface NetworkApi {
    WalletCheck checkAddress(String address);
    List<WalletCheck> getHistory();
}