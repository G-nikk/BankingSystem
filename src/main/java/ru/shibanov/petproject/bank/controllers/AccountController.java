package ru.shibanov.petproject.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shibanov.petproject.bank.services.AccountService;
import ru.shibanov.petproject.bank.services.UserService;

@Controller
@RequestMapping("/bank")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @Autowired
    public AccountController(final AccountService accountService, final UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping("/{id}/account/{account_id}")
    public String showAccount(final Model model, @PathVariable("account_id") final long account_id) {
        model.addAttribute("account", accountService.findById(account_id));
        return "show_account";
    }
}
