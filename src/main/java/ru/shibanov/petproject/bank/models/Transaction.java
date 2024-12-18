package ru.shibanov.petproject.bank.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "transaction_date", updatable = false, insertable = false)
    private LocalDateTime transactionDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "to_account_number")
    private BigInteger toAccountNumber;

    @Column(name = "from_account_number")
    private BigInteger fromAccountNumber;

    public Transaction() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigInteger getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(BigInteger fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public BigInteger getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(BigInteger toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }
}
