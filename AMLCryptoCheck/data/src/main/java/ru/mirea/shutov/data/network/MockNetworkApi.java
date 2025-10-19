package ru.mirea.shutov.data.network;

import java.util.Random;
import ru.mirea.shutov.domain.models.WalletCheck;

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
                System.currentTimeMillis()
        );
    }
}