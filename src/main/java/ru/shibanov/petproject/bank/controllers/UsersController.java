package ru.shibanov.petproject.bank.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shibanov.petproject.bank.models.Account;
import ru.shibanov.petproject.bank.models.User;
import ru.shibanov.petproject.bank.services.AccountService;
import ru.shibanov.petproject.bank.services.UserService;

@Controller
@RequestMapping("/bank")
public class UsersController {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public UsersController(final UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable final long id, Model model) {
        model.addAttribute("user", userService.findById(id));
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
    public String loginUser(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "login";
        long id = user.getId();
        return "redirect:/bank/"+id;
    }

    @GetMapping("/{id}/new-account")
    public String newAccount(@ModelAttribute("account") @Valid Account account, @PathVariable("id") long id) {
        account.setOwner(userService.findById(id));
        return "new_account";
    }

    @PostMapping("/{id}/new-account")
    public String newAccount(@PathVariable("id") final long id, @ModelAttribute("account") @Valid Account account, BindingResult bindingResult) {
        account.setOwner(userService.findById(id));
        if (bindingResult.hasErrors()){
            return "new_account";
        }
        accountService.save(account);
        return "redirect:/bank/"+id;
    }
}
