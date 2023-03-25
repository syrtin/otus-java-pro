package ru.otus.reflection;

import ru.otus.annotations.After;
import ru.otus.annotations.AnnotationType;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestHelper {
    public static void runTests(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<AnnotationType, List<Method>> testDataMap = getMethodsSortedInLists(clazz);

        List<Method> beforeMethods = testDataMap.get(AnnotationType.BEFORE);
        List<Method> testMethods = testDataMap.get(AnnotationType.TEST);
        List<Method> afterMethods = testDataMap.get(AnnotationType.AFTER);

        int testsPassed = 0;
        int testsFail = 0;

        for (Method testMethod : testMethods) {
            Object testObj = clazz.getDeclaredConstructor().newInstance();
            runBeforeMethods(beforeMethods, testObj);
            try {
                testMethod.invoke(testObj);
                System.out.printf("TEST [%s] PASSED \n", testMethod.getName());
                testsPassed++;
            } catch (Exception e) {
                System.out.printf("TEST [%s] FAILED \n", testMethod.getName());
                testsFail++;
            } finally {
                runAfterMethods(afterMethods, testObj);
            }
        }
        System.out.println("_________________________________________________");
        System.out.printf("%s TESTS PASSED | %s TESTS FAILED | %s TESTS TOTAL", testsPassed, testsFail, testMethods.size());
    }

    private static Map<AnnotationType, List<Method>> getMethodsSortedInLists(Class<?> clazz) {
        Map<AnnotationType, List<Method>> testDataMap = Map.of(
                AnnotationType.BEFORE, new ArrayList<>(),
                AnnotationType.AFTER, new ArrayList<>(),
                AnnotationType.TEST, new ArrayList<>());

        Method[] methodsAll = clazz.getDeclaredMethods();
        for (Method method : methodsAll) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Test) {
                    testDataMap.get(AnnotationType.TEST).add(method);
                }
                if (annotation instanceof Before) {
                    testDataMap.get(AnnotationType.BEFORE).add(method);
                }
                if (annotation instanceof After) {
                    testDataMap.get(AnnotationType.AFTER).add(method);
                }
            }
        }
        return testDataMap;
    }

    private static void runBeforeMethods(List<Method> beforeMethods, Object obj) {
        for (Method beforeMethod : beforeMethods) {
            try {
                beforeMethod.invoke(obj);
            } catch (Exception e) {
                System.out.printf("Warning! before-method %s failure! Results can be invalid \n", beforeMethod.toString());
            }
        }
    }

    private static void runAfterMethods(List<Method> beforeMethods, Object obj) {
        for (Method beforeMethod : beforeMethods) {
            try {
                beforeMethod.invoke(obj);
            } catch (Exception e) {
                System.out.printf("Warning! after-method %s failure! Results can be invalid \n", beforeMethod.toString());
            }
        }
    }
}