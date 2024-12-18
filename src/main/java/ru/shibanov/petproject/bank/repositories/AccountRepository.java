package ru.shibanov.petproject.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shibanov.petproject.bank.models.Account;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByOwnerId(long owner_id);
    Account findById(long account_id);
    Account findByAccountNumber(BigInteger account_number);
    List<Account> findByTypeLike(String type);
}