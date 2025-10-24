package ru.mirea.shutov.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.mirea.shutov.data.db.WalletCheckDao;
import ru.mirea.shutov.data.db.WalletCheckDbo;
import ru.mirea.shutov.domain.network.NetworkApi;
import ru.mirea.shutov.domain.models.WalletCheck;
import ru.mirea.shutov.domain.repository.WalletRepository;

public class WalletRepositoryImpl implements WalletRepository {

    private final NetworkApi networkApi;
    private final WalletCheckDao walletCheckDao;

    public WalletRepositoryImpl(NetworkApi networkApi, WalletCheckDao walletCheckDao) {
        this.networkApi = networkApi;
        this.walletCheckDao = walletCheckDao;
    }

    @Override
    public WalletCheck checkWallet(String address) {
        WalletCheck domainResult = networkApi.checkAddress(address);
        walletCheckDao.insert(mapToDbo(domainResult));
        return domainResult;
    }

    @Override
    public LiveData<List<WalletCheck>> getCheckHistory() {
        new Thread(() -> {
            List<WalletCheck> networkHistory = networkApi.getHistory();
            if (networkHistory != null && !networkHistory.isEmpty()) {
                List<WalletCheckDbo> dtoList = networkHistory.stream()
                        .map(this::mapToDbo)
                        .collect(Collectors.toList());
                walletCheckDao.insertAll(dtoList);
            }
        }).start();

        LiveData<List<WalletCheckDbo>> dtoListLiveData = walletCheckDao.getAll();
        return Transformations.map(dtoListLiveData, dtoList ->
                dtoList.stream().map(this::mapToDomain).collect(Collectors.toList())
        );
    }

    private WalletCheckDbo mapToDbo(WalletCheck domainModel) {
        WalletCheckDbo dbo = new WalletCheckDbo();
        dbo.address = domainModel.getAddress();
        dbo.riskScore = domainModel.getRiskScore();
        dbo.checkDate = domainModel.getCheckDate();
        dbo.currencyIconUrl = domainModel.getCurrencyIconUrl();
        return dbo;
    }

    private WalletCheck mapToDomain(WalletCheckDbo dbo) {
        return new WalletCheck(
                dbo.address,
                dbo.riskScore,
                dbo.checkDate,
                dbo.currencyIconUrl
        );
    }
}