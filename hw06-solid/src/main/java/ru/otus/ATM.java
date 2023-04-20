package ru.otus;

import java.util.Map;

public interface ATM {
    void acceptMoney(Map<Banknote, Integer> cassettes);

    Map<Banknote, Integer> withdrawMoney(int amount);

    int getBalance();
}