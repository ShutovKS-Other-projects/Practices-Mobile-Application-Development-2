package ru.mirea.shutov.domain.usecase;

import ru.mirea.shutov.domain.repository.AuthCallback;
import ru.mirea.shutov.domain.repository.AuthRepository;

public class RegisterUseCase {
    private final AuthRepository authRepository;

    public RegisterUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String email, String password, AuthCallback callback) {
        authRepository.register(email, password, callback);
    }
}
