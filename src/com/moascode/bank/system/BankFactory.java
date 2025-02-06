package com.moascode.bank.system;

public class BankFactory {
    private static Bank bank;

    private BankFactory() {
    }

    public static synchronized Bank getBank() {
        if (bank == null) {
            bank = new Bank();
        }
        return bank;
    }
}
