package ru.mirea.shutov.data.network;

import ru.mirea.shutov.domain.models.WalletCheck;

public interface NetworkApi {
    WalletCheck checkAddress(String address);
}