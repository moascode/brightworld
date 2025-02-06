package com.moascode.bank.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private final List<IBranch> branches;
    private final List<Account> accounts;
    private final Map<Account, List<Transaction>> transactions;

    public Bank() {
        this.branches = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.transactions = new HashMap<>();
    }

    public void addBranch(IBranch branch) {
        branches.add(branch);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void addTransaction(Transaction transaction) {
        List<Transaction> accountTransactions = transactions.computeIfAbsent(transaction.account(), k -> new ArrayList<>());
        accountTransactions.add(transaction);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Transaction> getTransactions(Account account, int limit) {
        return transactions.getOrDefault(account, new ArrayList<>()).stream().limit(limit).toList();
    }

}
