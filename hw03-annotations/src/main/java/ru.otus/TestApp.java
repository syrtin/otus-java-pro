package ru.otus;

import ru.otus.example.RoboTest;
import ru.otus.reflection.TestHelper;

import java.lang.reflect.InvocationTargetException;

public class TestApp {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestHelper.runTests(RoboTest.class);
    }
}