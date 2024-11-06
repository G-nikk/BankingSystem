package ru.shibanov.petproject.bank.controllers;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shibanov.petproject.bank.models.Account;
import ru.shibanov.petproject.bank.models.User;
import ru.shibanov.petproject.bank.repositories.UsersRepository;
import ru.shibanov.petproject.bank.services.AccountService;
import ru.shibanov.petproject.bank.services.UserService;

@Controller
@RequestMapping("/bank")
public class UsersController {
    private final UserService userService;
    private final AccountService accountService;
    private final UsersRepository usersRepository;
    private final EntityManager entityManager;

    @Autowired
    public UsersController(final UserService userService, AccountService accountService, UsersRepository usersRepository, @Qualifier("entityManager") EntityManager entityManager) {
        this.userService = userService;
        this.accountService = accountService;
        this.usersRepository = usersRepository;
        this.entityManager = entityManager;
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable final long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("accounts", accountService.findByOwnerId(userService.findById(id)));
        return "show_profile";
    }

    @GetMapping("/registration")
    public String newUser(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "registration";
        userService.save(user);
        long id = user.getId();
        return "redirect:/bank/"+id;
    }

    @GetMapping("/login")
    public String loginUser(Model model) {
        model.addAttribute("incorrectUsername", false);
        model.addAttribute("incorrectPassword", false);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        Model model) {
        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("incorrectUsername", true);
            return "login";
        } else if (user.getPassword().compareTo(password) != 0) {
            model.addAttribute("incorrectPassword", true);
            return "login";
        }
        long id = user.getId();
        return "redirect:/bank/"+id;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable final long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "edit_profile";
    }

    @PostMapping("/{id}/edit")
    public String editUser(@PathVariable long id, @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "edit_profile";
        }
        User userToEdit = userService.findById(id);
        userService.edit(userToEdit, user);
        return "redirect:/bank/"+id;
    }

    @PostMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/bank/login";
    }
}
