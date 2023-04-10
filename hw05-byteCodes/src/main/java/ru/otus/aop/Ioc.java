package ru.otus.aop;

import ru.otus.annotation.Log;
import ru.otus.example.TestLoggingInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Ioc {

    private Ioc() {
    }

    public static TestLoggingInterface createLogClass(TestLoggingInterface testLoggingImpl) {
        InvocationHandler handler = new LoggingInvocationHandler(testLoggingImpl);
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class LoggingInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface loggingClass;
        private final Set<String> loggedMethods;

        LoggingInvocationHandler(TestLoggingInterface loggingClass) {
            this.loggingClass = loggingClass;
            this.loggedMethods = getMethodsWithLogAnnotation(loggingClass);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (loggedMethods.contains(getMethodCode(method))) {
                System.out.printf("executed method: %s, %s%n", method.getName(), getParams(args));
            }
            return method.invoke(loggingClass, args);
        }

        private String getParams(Object[] args) {
            if (args != null) {
                String params = Arrays.stream(args)
                        .map(Object::toString)
                        .collect(Collectors.joining(","));
                String paramsPlural = args.length == 1 ? "param: " : "params: ";
                return paramsPlural + params;
            }
            return "no params";
        }

        private Set<String> getMethodsWithLogAnnotation(TestLoggingInterface testLogging) {
            return Arrays.stream(testLogging.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .map(this::getMethodCode)
                    .collect(Collectors.toSet());
        }

        private String getMethodCode(Method method) {
            return method.getName() + Arrays.toString(method.getParameters());
        }
    }
}