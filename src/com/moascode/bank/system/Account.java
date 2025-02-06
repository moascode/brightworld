package com.moascode.bank.system;

import java.math.BigDecimal;
import java.util.List;

public abstract class Account {
    int accountNumber;
    String customerName;
    private final Bank bank;
    private final IBranch branch;
    private BigDecimal balance;

    protected Account(IBranch branch, String customerName) {
        this.bank = BankFactory.getBank();
        this.accountNumber = bank.getAccounts().size() + 1;
        balance = BigDecimal.ZERO;
        this.branch = branch;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    protected void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    protected void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public List<Transaction> getTransactions(int limit) {
        return bank.getTransactions(this, limit);
    }

    @Override
    public String toString() {
        return "accountNumber=" + accountNumber;
    }
}
