package com.moascode.bank.system;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction (Account account, IBranch branch, BigDecimal amount, LocalDateTime date)  {
    public Transaction(Account account, IBranch branch, BigDecimal amount) {
        this(account, branch, amount, LocalDateTime.now());
    }
}
