package ru.otus.exception;

public class WithdrawException extends RuntimeException {
    public WithdrawException(String message) {
        super(message);
    }
}