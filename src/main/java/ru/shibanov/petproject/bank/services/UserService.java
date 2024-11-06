package ru.shibanov.petproject.bank.services;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shibanov.petproject.bank.models.User;
import ru.shibanov.petproject.bank.repositories.UsersRepository;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UsersRepository usersRepository;
    private final EntityManager entityManager;

    @Autowired
    public UserService(UsersRepository usersRepository, @Qualifier("entityManager") EntityManager entityManager) {
        this.usersRepository = usersRepository;
        this.entityManager = entityManager;
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
    public User save(User user) {
        usersRepository.save(user);
        return user;
    }

    @Transactional
    public void edit(User userToEdit, User userFromEdit) {
        userToEdit.setUsername(userFromEdit.getUsername());
        userToEdit.setFirstName(userFromEdit.getFirstName());
        userToEdit.setLastName(userFromEdit.getLastName());
        usersRepository.save(userToEdit);
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