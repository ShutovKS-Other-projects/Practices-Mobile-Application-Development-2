package ru.mirea.shutov.amlcryptocheck.presentation;

import androidx.annotation.Nullable;

public class LoginResult {

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }

    private final Status status;
    @Nullable
    private final String errorMessage;

    private LoginResult(Status status, @Nullable String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public static LoginResult loading() {
        return new LoginResult(Status.LOADING, null);
    }

    public static LoginResult success() {
        return new LoginResult(Status.SUCCESS, null);
    }

    public static LoginResult error(String message) {
        return new LoginResult(Status.ERROR, message);
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }
}