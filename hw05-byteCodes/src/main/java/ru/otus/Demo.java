package ru.otus;

import ru.otus.aop.Ioc;
import ru.otus.example.TestLogging;
import ru.otus.example.TestLoggingInterface;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface test = Ioc.createLogClass(new TestLogging());
        test.calculation();
        test.calculation(1);
        test.calculation(1, 2);
        test.calculation(1, 2, "3");
    }
}