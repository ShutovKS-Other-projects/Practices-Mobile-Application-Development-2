package ru.mirea.shutov.amlcryptocheck.presentation;

import androidx.annotation.Nullable;

public class RegisterResult {
    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }

    private final Status status;
    @Nullable
    private final String errorMessage;

    private RegisterResult(Status status, @Nullable String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public static RegisterResult loading() {
        return new RegisterResult(Status.LOADING, null);
    }

    public static RegisterResult success() {
        return new RegisterResult(Status.SUCCESS, null);
    }

    public static RegisterResult error(String message) {
        return new RegisterResult(Status.ERROR, message);
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }
}