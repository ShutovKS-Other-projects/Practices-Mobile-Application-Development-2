package ru.mirea.shutov.domain.repository;

public interface AuthCallback {
    void onSuccess();
    void onError(String message);
}
