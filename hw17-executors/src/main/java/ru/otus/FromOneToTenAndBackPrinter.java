package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FromOneToTenAndBackPrinter {
    private static final Logger logger = LoggerFactory.getLogger(FromOneToTenAndBackPrinter.class);
    private int threadIdTurn = 1;

    public static void main(String[] args) {
        var printer = new FromOneToTenAndBackPrinter();
        new Thread(() -> printer.count(1)).start();
        new Thread(() -> printer.count(2)).start();
    }

    private synchronized void count(int currentThreadId) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (int i = 1; i <= 10; i++) {

                    while (threadIdTurn != currentThreadId) {
                        this.wait();
                    }
                    threadIdTurn = (currentThreadId == 1) ? 2 : 1;

                    logger.info(String.valueOf(i));
                    sleep();
                    notifyAll();
                }
                for (int i = 9; i > 1; i--) {

                    while (threadIdTurn != currentThreadId) {
                        this.wait();
                    }
                    threadIdTurn = (currentThreadId == 1) ? 2 : 1;

                    logger.info(String.valueOf(i));
                    sleep();
                    notifyAll();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
