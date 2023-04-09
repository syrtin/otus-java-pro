package ru.otus;

import ru.otus.aop.Ioc;
import ru.otus.example.TestLoggingInterface;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface test = Ioc.createLogClass();
        test.calculation();
        test.calculation(1);
        test.calculation(1, 2);
        test.calculation(1, 2, "3");
    }
}