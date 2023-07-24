package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FromOneToTenAndBackPrinter {
    private static final Logger logger = LoggerFactory.getLogger(FromOneToTenAndBackPrinter.class);

    public static void main(String[] args) {
        var printer = new FromOneToTenAndBackPrinter();
        var t1 = new Thread(printer::count);
        var t2 = new Thread(printer::count);
        t1.start();
        try {
            t1.join(500);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
        t2.start();
    }

    public void count() {
        while (!Thread.currentThread().isInterrupted()) {
            for (int i = 1; i <= 10; i++) {
                logger.info(String.valueOf(i));
                sleep();
            }
            for (int i = 9; i > 1; i--) {
                logger.info(String.valueOf(i));
                sleep();
            }
        }
    }

    public void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
