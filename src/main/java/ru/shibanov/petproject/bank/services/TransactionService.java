package ru.shibanov.petproject.bank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shibanov.petproject.bank.models.Transaction;
import ru.shibanov.petproject.bank.repositories.TransactionRepository;

import java.math.BigInteger;
import java.util.List;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<Transaction> findAddingTransactions(BigInteger toAccountNumber) {
        return transactionRepository.findAllByToAccountNumberOrderByTransactionDate(toAccountNumber);
    }

    @Transactional(readOnly = true)
    public List<Transaction> findDebitingTransactions(BigInteger fromAccountNumber) {
        return transactionRepository.findByFromAccountNumberOrderByTransactionDate(fromAccountNumber);
    }
}
