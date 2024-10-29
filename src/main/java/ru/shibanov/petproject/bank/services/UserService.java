package ru.shibanov.petproject.bank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shibanov.petproject.bank.models.User;
import ru.shibanov.petproject.bank.repositories.UsersRepository;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UsersRepository usersRepository;
    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(long id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Transactional
    public User save (User user) {
        usersRepository.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Transactional
    public void delete(long id) {
        usersRepository.deleteById(id);
    }
}