package com.moascode.bank;

import com.moascode.bank.system.Account;
import com.moascode.bank.system.Branch;

import java.math.BigDecimal;

public class BankSystemTest {
    public static void main(String... args) {
        Branch branch1 = new Branch("KL");
        Branch branch2 = new Branch("PJ");

        Account account1 = branch1.registerAccount("John Doe");
        Account account2 = branch2.registerAccount("John Sina");

        branch1.deposit(account1, new BigDecimal(1000));
        branch2.deposit(account2, new BigDecimal(2000));

        branch1.withdraw(account1, new BigDecimal(500));
        branch2.withdraw(account2, new BigDecimal(1000));
        branch2.withdraw(account2, new BigDecimal(200));

        System.out.println("Account 1 transactions: " + account1.getTransactions(10));
        System.out.println("Account 1 balance: " + account1.getBalance());

        System.out.println("Account 2 transactions: " + account2.getTransactions(10));
        System.out.println("Account 2 balance: " + account2.getBalance());
    }
}
