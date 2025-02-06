package com.moascode.bank.system;

import java.math.BigDecimal;

public interface IBranch {
    Account registerAccount(String customerName);
    void withdraw(Account account, BigDecimal amount);
    void deposit(Account account, BigDecimal amount);
}
