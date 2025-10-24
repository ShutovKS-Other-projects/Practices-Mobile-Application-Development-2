package ru.mirea.shutov.amlcryptocheck.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.shutov.domain.repository.AuthRepository;

public class ProfileViewModel extends ViewModel {

    private final AuthRepository authRepository;

    private final MutableLiveData<String> userEmail = new MutableLiveData<>();
    private final MutableLiveData<Boolean> logoutEvent = new MutableLiveData<>();

    public LiveData<String> getUserEmail() {
        return userEmail;
    }

    public LiveData<Boolean> getLogoutEvent() {
        return logoutEvent;
    }

    public ProfileViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void loadUserData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userEmail.setValue(currentUser.getEmail());
        } else {
            userEmail.setValue("No user logged in");
        }
    }

    public void logout() {
        authRepository.logout();
        logoutEvent.setValue(true);
    }
}