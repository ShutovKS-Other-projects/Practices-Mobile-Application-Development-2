package ru.mirea.shutov.data.repository;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import ru.mirea.shutov.domain.repository.AuthCallback;
import ru.mirea.shutov.domain.repository.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {
    private final FirebaseAuth mAuth;

    public AuthRepositoryImpl() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void login(String email, String password, AuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onError(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    @Override
    public void register(String email, String password, AuthCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onError(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    @Override
    public void logout() {
        mAuth.signOut();
    }

    @Override
    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }
}