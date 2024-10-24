package ru.shibanov.petproject.bank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shibanov.petproject.bank.models.Account;
import ru.shibanov.petproject.bank.models.User;
import ru.shibanov.petproject.bank.repositories.UserRepository;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User save (User user) {
        userRepository.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}