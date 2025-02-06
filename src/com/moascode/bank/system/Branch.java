package com.moascode.bank.system;

import java.math.BigDecimal;

public class Branch implements IBranch {
    private final Bank bank;
    private final String branchName;

    public Branch(String branchName) {
        this.bank = BankFactory.getBank();
        this.branchName = branchName;
        bank.addBranch(this);
    }

    @Override
    public Account registerAccount(String customerName) {
        Account account = new CurrentAccount(this, customerName);
        bank.addAccount(account);
        return account;
    }

    @Override
    public void withdraw(Account account, BigDecimal amount) {
        Transaction transaction = new Transaction(account, this, amount.negate());
        bank.addTransaction(transaction);
        account.withdraw(amount);
    }

    @Override
    public void deposit(Account account, BigDecimal amount) {
        Transaction transaction = new Transaction(account, this, amount);
        bank.addTransaction(transaction);
        account.deposit(amount);
    }

    @Override
    public String toString() {
        return "branchName=" + branchName;
    }
}
