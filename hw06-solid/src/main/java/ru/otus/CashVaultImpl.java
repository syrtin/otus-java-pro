package ru.otus;

import ru.otus.exception.WithdrawException;

import java.util.HashMap;
import java.util.Map;

public class CashVaultImpl implements CashVault {

    private Map<Banknote, Integer> cassette;

    @Override
    public Map<Banknote, Integer> withdrawMoney(int money) {
        int currentMoney = money;
        Map<Banknote, Integer> result = new HashMap<>();
        Banknote[] banknoteTypes = Banknote.values();
        for (Banknote banknoteType : banknoteTypes) {
            int neededAmount = getQuantityOfBanknotes(banknoteType, currentMoney);
            if (neededAmount != 0)
                result.put(banknoteType, neededAmount);
            currentMoney -= neededAmount * banknoteType.nominal;
        }

        if (result.isEmpty())
            throw new WithdrawException("Required sum isn't multiple of any available banknotes");
        if (currentMoney != 0)
            throw new WithdrawException("Required sum can't be withdraw due to lack of banknotes in ATM");

        result.forEach((k, v) -> cassette.computeIfPresent(k, (k1, v1) -> (v1 + v)));

        System.out.printf("Banknotes %s successfully withdraw by sum of %s rubles %n", result, money);
        return result;
    }

    @Override
    public void storeMoney(Map<Banknote, Integer> newCassette) {
        newCassette.forEach((key, value) -> cassette.merge(key, value, Integer::sum));
        System.out.printf("Banknotes %s successfully added%n", newCassette);
    }

    private int getQuantityOfBanknotes(Banknote banknote, int amount) {
        int resultQuantity = 0;
        int cassetteQuantity = cassette.get(banknote);
        int divider = banknote.getNominal();
        if (divider <= amount) {
            resultQuantity = amount / divider;
            if (cassetteQuantity < resultQuantity)
                resultQuantity = cassetteQuantity;
        }
        return resultQuantity;
    }

    private Map<Banknote, Integer> getImmutableMapOfBanknotes() {
        return Map.of(
                Banknote.R100, 0,
                Banknote.R200, 0,
                Banknote.R500, 0,
                Banknote.R1000, 0,
                Banknote.R2000, 0,
                Banknote.R5000, 0);
    }

    public CashVaultImpl() {
        this.cassette = new HashMap<>(getImmutableMapOfBanknotes());
    }
}