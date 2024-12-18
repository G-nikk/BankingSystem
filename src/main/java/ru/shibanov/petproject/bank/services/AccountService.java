package ru.shibanov.petproject.bank.services;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shibanov.petproject.bank.models.Account;
import ru.shibanov.petproject.bank.models.User;
import ru.shibanov.petproject.bank.repositories.AccountRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final EntityManager entityManager;

    @Autowired
    public AccountService(AccountRepository accountRepository, @Qualifier("entityManager") EntityManager entityManager) {
        this.accountRepository = accountRepository;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<Account> findByOwnerId(User user) {
        return accountRepository.findByOwnerId(user.getId());
    }

    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Account findById(long id) {
        return accountRepository.findById(id);
    }

    @Transactional
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Transactional
    public Account findByAccountNumber(BigInteger accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Transactional
    public void transfer(BigInteger to_accountNumber, BigInteger from_accountNumber, BigDecimal amount) {
        Account fromAccount = findByAccountNumber(from_accountNumber);
        Account toAccount = findByAccountNumber(to_accountNumber);
        toAccount.setBalance(toAccount.getBalance().add(amount));
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        save(fromAccount);
        save(toAccount);
    }

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void applyInterest(){
        List<Account> accounts = accountRepository.findByTypeLike("Сберегательный%");
        for (Account account : accounts) {
            account.setBalance(account.getBalance().add(account.getBalance().multiply(BigDecimal.valueOf(Account.extractInterestRate(account.getType()) / 100))));
            accountRepository.save(account);
        }
    }

    @Transactional
    public void delete(long id) {
        accountRepository.deleteById(id);
    }
}
