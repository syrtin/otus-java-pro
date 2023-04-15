package ru.otus;

import lombok.Setter;
import ru.otus.exception.WithdrawException;

import java.util.Map;

public class ATMImpl implements ATM {
    private final CashVault cashVault = new CashVaultImpl();
    @Setter
    private int balance = 0;

    @Override
    public void acceptMoney(Map<Banknote, Integer> cassettes) {
        balance += cassettes.entrySet().stream()
                .map((entry -> entry.getKey().getNominal() * entry.getValue()))
                .reduce(Integer::sum)
                .orElse(0);
        cashVault.storeMoney(cassettes);
    }

    @Override
    public Map<Banknote, Integer> withdrawMoney(int amount) {
        if (balance < amount)
            throw new WithdrawException("Not enough money in this ATM");
        balance -= amount;
        return cashVault.withdrawMoney(amount);
    }

    @Override
    public Integer getBalance() {
        return balance;
    }
}