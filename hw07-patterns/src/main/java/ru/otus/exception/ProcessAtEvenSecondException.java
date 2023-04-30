package ru.otus.exception;

public class ProcessAtEvenSecondException extends RuntimeException {
    public ProcessAtEvenSecondException(String message) {
        super(message);
    }
}