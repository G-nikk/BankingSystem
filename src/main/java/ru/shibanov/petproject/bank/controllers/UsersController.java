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

    @GetMapping("/{id}/new-account")
    public String newAccount(@PathVariable long id, Model model) {
        model.addAttribute("account", new Account());
        return "new_account";
    }

    @PostMapping("/{id}/new-account")
    public String newAccount(@PathVariable("id") final long id, @ModelAttribute("account") @Valid Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "new_account";
        }
        account.setOwner(userService.findById(id));
        account.setAccountNumber();
        accountService.save(account);
        return "redirect:/bank/"+id;
    }

    @PostMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/bank/login";
    }
}
