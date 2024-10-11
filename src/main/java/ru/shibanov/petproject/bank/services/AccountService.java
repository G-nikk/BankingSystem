package ru.shibanov.petproject.bank.services;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shibanov.petproject.bank.models.Account;
import ru.shibanov.petproject.bank.models.User;
import ru.shibanov.petproject.bank.repositories.AccountRepository;

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

    @Transactional
    public void save(Account account) {
        accountRepository.save(account);
    }
}
