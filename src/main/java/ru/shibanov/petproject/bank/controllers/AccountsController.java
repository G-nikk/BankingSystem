package ru.shibanov.petproject.bank.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shibanov.petproject.bank.models.Account;
import ru.shibanov.petproject.bank.models.Transaction;
import ru.shibanov.petproject.bank.repositories.AccountRepository;
import ru.shibanov.petproject.bank.services.AccountService;
import ru.shibanov.petproject.bank.services.TransactionService;
import ru.shibanov.petproject.bank.services.UserService;

import java.math.BigDecimal;

@Controller
@RequestMapping("/bank")
public class AccountsController {
    private final AccountService accountService;
    private final UserService userService;
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountsController(final AccountService accountService, final UserService userService, TransactionService transactionService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.userService = userService;
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/{id}/new-account")
    public String newAccount(@PathVariable long id, Model model) {
        model.addAttribute("account", new Account());
        return "new_account";
    }

    @PostMapping("/{id}/new-account")
    public String newAccount(@PathVariable("id") final long id, @ModelAttribute("account") @Valid Account account, BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()){
            return "new_account";
        }
        account.setOwner(userService.findById(id));
        account.setAccountNumber();
        accountService.save(account);
        return "redirect:/bank/"+id;
    }

    @GetMapping("/{id}/account/{account_id}")
    public String showAccount(final Model model, @PathVariable("account_id") final long account_id, @PathVariable("id") final long id) {
        model.addAttribute("account", accountService.findById(account_id));
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("debiting_transactions", transactionService.findDebitingTransactions(accountService.findById(account_id).getAccountNumber()));
        model.addAttribute("adding_transactions", transactionService.findAddingTransactions(accountService.findById(account_id).getAccountNumber()));
        return "show_account";
    }

    @GetMapping("/transfer/{id}")
    public String showTransfer(Model model) {
        model.addAttribute("incorrectAmount", false);
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("incorrectAccountNumber", false);
        return "transfer";
    }

    @PostMapping("/transfer/{id}")
    public String transfer(@PathVariable("id") final long from_id, @RequestParam BigDecimal amount, @ModelAttribute("transaction") @Valid Transaction transaction,
                           Model model) {
        if (accountService.findById(from_id).getBalance().compareTo(amount) == -1) {
            model.addAttribute("incorrectAmount", true);
            return "transfer";
        }
        else if (accountService.findByAccountNumber(transaction.getToAccountNumber()) == null) {
            model.addAttribute("incorrectAccountNumber", true);
            return "transfer";
        }
        transaction.setType("Перевод");
        transaction.setFromAccountNumber(accountRepository.findById(from_id).getAccountNumber());
        accountService.transfer(transaction.getToAccountNumber(), transaction.getFromAccountNumber(), transaction.getAmount());
        transactionService.save(transaction);
        long owner_from_id = accountService.findById(from_id).getOwner().getId();
        return "redirect:/bank/" + owner_from_id + "/account/" + from_id;
    }

    @PostMapping("/{id}/account/{account_id}")
    public String delete(@PathVariable("account_id") final long account_id, @PathVariable("id") final long id) {
        accountService.delete(account_id);
        return "redirect:/bank/" + id;
    }
}
