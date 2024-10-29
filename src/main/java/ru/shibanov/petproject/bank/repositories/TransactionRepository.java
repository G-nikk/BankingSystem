package ru.shibanov.petproject.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shibanov.petproject.bank.models.Transaction;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List <Transaction> findAllByToAccountNumberOrderByTransactionDate(BigInteger toAccountNumber);
    List <Transaction> findByFromAccountNumberOrderByTransactionDate(BigInteger fromAccountNumber);
}