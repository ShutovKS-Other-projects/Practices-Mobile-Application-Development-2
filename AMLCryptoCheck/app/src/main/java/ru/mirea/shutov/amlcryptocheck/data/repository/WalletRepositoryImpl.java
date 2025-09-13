package ru.mirea.shutov.amlcryptocheck.data.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.mirea.shutov.amlcryptocheck.domain.model.WalletCheck;
import ru.mirea.shutov.amlcryptocheck.domain.repository.WalletRepository;

public class WalletRepositoryImpl implements WalletRepository {

    @Override
    public WalletCheck checkWallet(String address) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new WalletCheck(
                address,
                85,
                System.currentTimeMillis()
        );
    }

    @Override
    public List<WalletCheck> getCheckHistory() {
        List<WalletCheck> history = new ArrayList<>();

        history.add(new WalletCheck(
                "0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae",
                15,
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)
        ));
        history.add(new WalletCheck(
                "0xAb5801a7D398351b8bE11C439e05C5B3259aeC9B",
                90,
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2)
        ));

        return history;
    }
}