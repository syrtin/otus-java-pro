package ru.otus;

import ru.otus.exception.WithdrawException;

import java.util.Map;

public class ATMImpl implements ATM {
    private final CashVault cashVault = new CashVaultImpl(CashVaultImpl.getCassette());

    @Override
    public void acceptMoney(Map<Banknote, Integer> cassettes) {
        cashVault.storeMoney(cassettes);
    }

    @Override
    public Map<Banknote, Integer> withdrawMoney(int amount) {
        if (getBalance() < amount) {
            throw new WithdrawException("Not enough money in this ATM");
        }
        return cashVault.withdrawMoney(amount);
    }

    @Override
    public int getBalance() {
        return cashVault.getBalance();
    }
}