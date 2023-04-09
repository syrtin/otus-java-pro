package ru.otus.example;

import ru.otus.annotation.Log;

public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public void calculation() {
        System.out.printf("Zero%n");
    }

    @Log
    @Override
    public void calculation(int param1) {
        System.out.printf("Total is: %d%n", param1);
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        System.out.printf("Total is: %d%n", param1 + param2);
    }

    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.printf("Total is: %d%n", param1 + param2 + Integer.parseInt(param3));
    }
}