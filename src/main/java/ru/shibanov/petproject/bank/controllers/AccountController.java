package ru.shibanov.petproject.bank.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shibanov.petproject.bank.models.Account;
import ru.shibanov.petproject.bank.models.Transaction;
import ru.shibanov.petproject.bank.services.AccountService;
import ru.shibanov.petproject.bank.services.TransactionService;
import ru.shibanov.petproject.bank.services.UserService;

@Controller
@RequestMapping("/bank")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public AccountController(final AccountService accountService, final UserService userService, TransactionService transactionService) {
        this.accountService = accountService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}/account/{account_id}")
    public String showAccount(final Model model, @PathVariable("account_id") final long account_id) {
        model.addAttribute("account", accountService.findById(account_id));
        return "show_account";
    }

    @GetMapping("/transfer/{id}")
    public String showTransfer(@PathVariable("id") final long account_id) {
        return "transfer";
    }

    @PostMapping("/transfer/{id}")
    public String transfer(@PathVariable("id") final long from_id, Model model, @ModelAttribute @Valid Transaction transaction, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "transfer";
        }
        accountService.transfer(transaction.getToAccount().getAccountNumber(), transaction.getFromAccount().getAccountNumber(), transaction.getAmount());
        transactionService.save(transaction);
        long owner_from_id = accountService.findById(from_id).getOwner().getId();
        return "redirect:/bank/" + owner_from_id + "/account/" + from_id;
    }
}
