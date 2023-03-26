package ru.otus.reflection;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> testMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();

    private int testsPassed = 0;
    private int testsFail = 0;

    public static void runTests(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        TestRunner testRunner = new TestRunner();
        testRunner.runTestClass(clazz);
    }

    private void runTestClass(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        organizeMethodsInLists(clazz);

        for (Method testMethod : testMethods) {
            Object testObj = clazz.getDeclaredConstructor().newInstance();
            runMethods(beforeMethods, testObj);
            try {
                testMethod.invoke(testObj);
                System.out.printf("TEST [%s] PASSED \n", testMethod.getName());
                testsPassed++;
            } catch (Exception e) {
                System.out.printf("TEST [%s] FAILED \n", testMethod.getName());
                testsFail++;
            } finally {
                runMethods(afterMethods, testObj);
            }
        }
        System.out.println("_________________________________________________");
        System.out.printf("%s TESTS PASSED | %s TESTS FAILED | %s TESTS TOTAL", testsPassed, testsFail, testMethods.size());
    }

    private void organizeMethodsInLists(Class<?> clazz) {
        Method[] methodsAll = clazz.getDeclaredMethods();
        for (Method method : methodsAll) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Test) {
                    testMethods.add(method);
                }
                if (annotation instanceof Before) {
                    beforeMethods.add(method);
                }
                if (annotation instanceof After) {
                    afterMethods.add(method);
                }
            }
        }
    }

    private void runMethods(List<Method> methods, Object obj) {
        for (Method method : methods) {
            try {
                method.invoke(obj);
            } catch (Exception e) {
                System.out.printf("Warning! %s method failed! Results can be invalid \n", method.toString());
            }
        }
    }
}