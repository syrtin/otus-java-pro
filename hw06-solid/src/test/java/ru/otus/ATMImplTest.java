package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.exception.WithdrawException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ATMImplTest {

    private static final String NOT_ENOUGH_MONEY_MSG = "Not enough money in this ATM";
    private static final String NOT_MULTIPLE_SUM_MSG = "Required sum isn't multiple of any available banknotes";
    private static final String LACK_OF_BANKNOTES_MSG = "Required sum can't be withdraw due to lack of banknotes in ATM";

    @Test
    @DisplayName("Check of money accept operation and balance changes afterwards")
    void acceptMoneyAndBalanceCheckTest() {
        ATM atm = new ATMImpl();
        assertEquals(0, atm.getBalance());
        atm.acceptMoney(getTestCassetteMap());
        assertEquals(25800, atm.getBalance());
    }

    @Test
    @DisplayName("Check of money withdraw operation and balance changes afterwards")
    void withdrawMoneyAndBalanceCheckTest() {
        ATM atm = new ATMImpl();
        atm.acceptMoney(getTestCassetteMap());
        atm.withdrawMoney(5800);
        assertEquals(20000, atm.getBalance());
    }

    @Test
    @DisplayName("Check of Exception thrown due to not enough money at ATM on its balance")
    void withdrawMoneyThrowsExceptionCausedByNotEnoughMoneyOnBalanceTest() {
        ATM atm = new ATMImpl();
        atm.acceptMoney(getTestCassetteMap());
        WithdrawException thrown = assertThrows(WithdrawException.class,
                () -> atm.withdrawMoney(26000));
        assertEquals(NOT_ENOUGH_MONEY_MSG, thrown.getMessage());
    }

    @Test
    @DisplayName("Check of Exception thrown due to the lack of required banknotes")
    void withdrawMoneyThrowsExceptionCausedByLackOfRequiredBanknotesTest() {
        ATM atm = new ATMImpl();
        atm.acceptMoney(getTestCassetteMap());
        WithdrawException thrown = assertThrows(WithdrawException.class,
                () -> atm.withdrawMoney(900));
        assertEquals(LACK_OF_BANKNOTES_MSG, thrown.getMessage());
    }

    @Test
    @DisplayName("Check of Exception thrown due to sum that has no multiple of possible banknotes")
    void withdrawMoneyThrowsExceptionCausedByNotMultipleDividerSumTest() {
        ATM atm = new ATMImpl();
        atm.acceptMoney(getTestCassetteMap());
        WithdrawException thrown = assertThrows(WithdrawException.class,
                () -> atm.withdrawMoney(50));
        assertEquals(NOT_MULTIPLE_SUM_MSG, thrown.getMessage());
    }

    private Map<Banknote, Integer> getTestCassetteMap() {
        return Map.of(
                Banknote.R100, 1,
                Banknote.R200, 1,
                Banknote.R500, 1,
                Banknote.R1000, 10,
                Banknote.R2000, 5,
                Banknote.R5000, 1);
    }
}