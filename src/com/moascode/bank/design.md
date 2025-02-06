# Design Bank

## Components

- Bank () -> addBranch(), addAccount(), addTransaction()
- Branch (Bank) //can be physical/online, make an interface
- Account (Bank, Branch) //can be saving/current, make an interface
- Transaction (Account, Branch)

## Interfaces

- IBranch
  - addAccount(Account)
  - withdraw(Account, amount)
  - deposit(Account, amount)

- IAccount
  - getBalance()
  - getTransactions()

## Classes

```java

public class Transaction {
    private Account account;
    private Branch branch;
    private BigDecimal amount;
    private LocalDateTime date;

    public Transaction(Account account, Branch branch, BigDecimal amount) {
        this.account = account;
        this.branch = branch;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }
}

```

