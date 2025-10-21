package ru.mirea.shutov.amlcryptocheck.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.mirea.shutov.domain.repository.AuthCallback;
import ru.mirea.shutov.domain.usecase.RegisterUseCase;

public class RegisterViewModel extends ViewModel {

    private final RegisterUseCase registerUseCase;

    private final MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();

    public LiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public RegisterViewModel(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    public void register(String email, String password) {
        registerResult.setValue(RegisterResult.loading());

        registerUseCase.execute(email, password, new AuthCallback() {
            @Override
            public void onSuccess() {
                registerResult.setValue(RegisterResult.success());
            }

            @Override
            public void onError(String message) {
                registerResult.setValue(RegisterResult.error(message));
            }
        });
    }
}