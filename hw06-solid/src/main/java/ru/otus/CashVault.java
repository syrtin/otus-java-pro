package ru.otus;

import java.util.Map;

public interface CashVault {
    Map<Banknote, Integer> withdrawMoney(int money);

    void storeMoney(Map<Banknote, Integer> cassette);
}