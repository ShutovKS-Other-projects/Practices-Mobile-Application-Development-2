package ru.mirea.shutov.amlcryptocheck.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.shutov.domain.repository.AuthCallback;
import ru.mirea.shutov.domain.usecase.LoginUseCase;

public class LoginViewModel extends ViewModel {

    private final LoginUseCase loginUseCase;

    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public void login(String email, String password) {
        loginResult.setValue(LoginResult.loading());

        loginUseCase.execute(email, password, new AuthCallback() {
            @Override
            public void onSuccess() {
                loginResult.setValue(LoginResult.success());
            }

            @Override
            public void onError(String message) {
                loginResult.setValue(LoginResult.error(message));
            }
        });
    }
}