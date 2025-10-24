package ru.mirea.shutov.data.network;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import ru.mirea.shutov.domain.models.WalletCheck;
import ru.mirea.shutov.domain.network.NetworkApi;

public class MockNetworkApi implements NetworkApi {
    @Override
    public WalletCheck checkAddress(String address) {

        // Имитируем задержку сети
        try {
            Thread.sleep(1000); // 1 секунда
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Возвращаем случайный результат
        return new WalletCheck(
                address,
                new Random().nextInt(100), // Случайный риск от 0 до 99
                System.currentTimeMillis(),
                "https://example.com/icon.png"
        );
    }

    @Override
    public List<WalletCheck> getHistory() {
        // Возвращаем пустой список для истории
        return Collections.emptyList();
    }
}