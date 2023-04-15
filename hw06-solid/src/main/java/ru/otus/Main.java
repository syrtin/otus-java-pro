package ru.otus;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATMImpl();
        System.out.printf("Initial balance is %s rubles%n", atm.getBalance());
        atm.acceptMoney(Map.of(
                Banknote.R100, 10,
                Banknote.R200, 10,
                Banknote.R500, 10,
                Banknote.R1000, 10,
                Banknote.R2000, 10,
                Banknote.R5000, 10));
        System.out.printf("Intermediate balance is %s rubles%n", atm.getBalance());
        atm.withdrawMoney(80000);
        System.out.printf("Final balance is %s rubles%n", atm.getBalance());
    }
}