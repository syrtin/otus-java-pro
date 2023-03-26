package ru.otus.exception;

public class TestFailException extends RuntimeException {
    public TestFailException(String message) {
        super(message);
    }
}