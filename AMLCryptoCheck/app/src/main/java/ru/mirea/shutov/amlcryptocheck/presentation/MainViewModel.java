package ru.mirea.shutov.amlcryptocheck.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.shutov.domain.models.WalletCheck;
import ru.mirea.shutov.domain.usecase.CheckWalletUseCase;
import ru.mirea.shutov.domain.usecase.GetCheckHistoryUseCase;

public class MainViewModel extends ViewModel {

    private final CheckWalletUseCase checkWalletUseCase;
    private final GetCheckHistoryUseCase getCheckHistoryUseCase;

    private LiveData<List<WalletCheck>> historyFromDb;
    private MutableLiveData<WalletCheck> singleCheckResult = new MutableLiveData<>();

    private MediatorLiveData<List<WalletCheck>> historyToDisplay = new MediatorLiveData<>();

    public MainViewModel(CheckWalletUseCase checkWalletUseCase, GetCheckHistoryUseCase getCheckHistoryUseCase) {
        this.checkWalletUseCase = checkWalletUseCase;
        this.getCheckHistoryUseCase = getCheckHistoryUseCase;

        historyFromDb = this.getCheckHistoryUseCase.execute();

        historyToDisplay.addSource(historyFromDb, dbList -> {
            historyToDisplay.setValue(dbList);
        });

        historyToDisplay.addSource(singleCheckResult, networkResult -> {
            if (networkResult != null) {
                List<WalletCheck> currentList = historyFromDb.getValue();
                if (currentList == null) {
                    currentList = new ArrayList<>();
                }
                List<WalletCheck> combinedList = new ArrayList<>(currentList);
                combinedList.add(0, networkResult);
                historyToDisplay.setValue(combinedList);
            }
        });
    }

    public LiveData<List<WalletCheck>> getHistoryToDisplay() {
        return historyToDisplay;
    }

    public void onCheckRiskClicked(String address) {
        new Thread(() -> {
            WalletCheck result = checkWalletUseCase.execute(address);
            singleCheckResult.postValue(result);
        }).start();
    }
}