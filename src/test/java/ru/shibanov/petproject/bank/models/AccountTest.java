package ru.shibanov.petproject.bank.models;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void extractInterestRate_ValidAccountName() {
        assertEquals(5.0, Account.extractInterestRate("Сберегательный 5%"));
        assertEquals(20.0, Account.extractInterestRate("Сберегательный 20%"));
        assertEquals(1.0, Account.extractInterestRate("Сберегательный 1%"));
    }
}