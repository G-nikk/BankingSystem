package ru.shibanov.petproject.bank.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "account_number")
    private BigInteger accountNumber;

    @Column(name = "type")
    private String type;

    @NotNull(message = "Укажите баланс счёта!")
    @Min(value = 0, message = "Бланс должен быть больше нуля!")
    @Max(value = 1000000000, message = "Баланс не должен быть больше миллиарда!")
    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "interest_rate")
    private double interestRate;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    public Account() {
    }

    public BigInteger getAccountNumber() {
        return accountNumber;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public User getOwner() {
        return owner;
    }

    public void setAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // Первая цифра не может быть нулём
        sb.append(random.nextInt(9) + 1); // Генерируем случайную цифру от 1 до 9

        // Генерируем 10 двухзначных чисел
        for (int i = 0; i <= 18; i++) {
            sb.append(random.nextInt(9));
        }

        this.accountNumber = new BigInteger(sb.toString());
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getHiddenAccountNumber(){
        String str = this.accountNumber.toString();
        return "****************" + this.accountNumber.toString().substring(16);
    }
}