package ru.otus;

import java.util.Map;

public interface CashVault {
    int getBalance();

    Map<Banknote, Integer> withdrawMoney(int money);

    void storeMoney(Map<Banknote, Integer> cassette);
}