package ru.otus;

import ru.otus.exception.WithdrawException;

import java.util.HashMap;
import java.util.Map;

public class CashVaultImpl implements CashVault {
    private final Map<Banknote, Integer> cassette;

    public static Map<Banknote, Integer> getCassette() {
        return Map.of(
                Banknote.R100, 0,
                Banknote.R200, 0,
                Banknote.R500, 0,
                Banknote.R1000, 0,
                Banknote.R2000, 0,
                Banknote.R5000, 0);
    }

    public CashVaultImpl(Map<Banknote, Integer> initialCassette) {
        this.cassette = new HashMap<>(initialCassette);
    }

    @Override
    public int getBalance() {
        return cassette.entrySet().stream()
                .map((entry -> entry.getKey().getNominal() * entry.getValue()))
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    public Map<Banknote, Integer> withdrawMoney(int money) {
        int currentMoney = money;
        Map<Banknote, Integer> result = new HashMap<>();
        Banknote[] banknoteTypes = Banknote.values();
        for (Banknote banknoteType : banknoteTypes) {
            int neededAmount = getQuantityOfBanknotes(banknoteType, currentMoney);
            if (neededAmount != 0) {
                result.put(banknoteType, neededAmount);
            }
            currentMoney -= neededAmount * banknoteType.nominal;
        }

        if (result.isEmpty()) {
            throw new WithdrawException("Required sum isn't multiple of any available banknotes");
        }
        if (currentMoney != 0) {
            throw new WithdrawException("Required sum can't be withdraw due to lack of banknotes in ATM");
        }
        for (Map.Entry<Banknote, Integer> entry : result.entrySet()) {
            cassette.computeIfPresent(entry.getKey(), (k1, v1) -> (v1 - entry.getValue()));
        }
        System.out.printf("Banknotes %s successfully withdraw by sum of %s rubles %n", result, money);
        return result;
    }

    @Override
    public void storeMoney(Map<Banknote, Integer> newCassette) {
        for (Map.Entry<Banknote, Integer> entry : newCassette.entrySet()) {
            cassette.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
        System.out.printf("Banknotes %s successfully added%n", newCassette);
    }

    private int getQuantityOfBanknotes(Banknote banknote, int amount) {
        int resultQuantity = 0;
        int cassetteQuantity = cassette.get(banknote);
        int divider = banknote.getNominal();
        if (divider <= amount) {
            resultQuantity = amount / divider;
            if (cassetteQuantity < resultQuantity) {
                resultQuantity = cassetteQuantity;
            }
        }
        return resultQuantity;
    }
}