package ru.mirea.shutov.domain.usecase;

import ru.mirea.shutov.domain.repository.AuthRepository;

public class CheckUserLoggedInUseCase {
    private final AuthRepository authRepository;

    public CheckUserLoggedInUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public boolean execute() {
        return authRepository.isUserLoggedIn();
    }
}
