package ru.shibanov.petproject.bank.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    @NotEmpty(message = "Юзернейм не должен быть пустым!")
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$", message = "Юзернейм должен содержать от 3 до 15 символов и состоять только из букв, цифр и нижних подчёркиваний!")
    private String username;

    @Column(name = "first_name")
    @Pattern(regexp = "^[А-Я][а-яё]+(?:-[А-Я][а-яё]+)*$", message = "Имя должно быть на русском языке, начинаться с большой буквы и иметь длину от 2 до 20 символов.")
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp = "^[А-Я][а-яё]+(?:-[А-Я][а-яё]+)*$", message = "Фамилия должна быть на русском языке, начинаться с большой буквы и иметь длину от 2 до 20 символов.")
    private String lastName;

    @NotEmpty(message = "Придумайте пароль!")
    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    @Pattern(regexp = "^\\+7[0-9]{10}$", message = "Введите номер телефона без пробелов и иных разделителей, начиная с +7")
    private String phoneNumber;

    @Column(name = "email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Некорректный email!")
    private String email;

    @Column(name = "security_question")
    @NotEmpty(message = "Придумайте секретный вопрос!")
    private String securityQuestion;

    @Column(name = "security_answer")
    @NotEmpty(message = "Заполните поле ответа на секретный вопрос!")
    private String securityAnswer;

    @OneToMany(mappedBy = "owner", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Account> accounts;

    public void AddAccount(Account account) {
        accounts.add(account);
    }

    public User(String username, String firstName, String lastName, String password, String phoneNumber, String email, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotEmpty(message = "Юзернейм не должен быть пустым!") @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$", message = "Юзернейм должен содержать от 3 до 15 символов и состоять только из букв, цифр и нижних подчёркиваний!") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Юзернейм не должен быть пустым!") @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$", message = "Юзернейм должен содержать от 3 до 15 символов и состоять только из букв, цифр и нижних подчёркиваний!") String username) {
        this.username = username;
    }

    public @Pattern(regexp = "^[А-Я][а-яё]+(?:-[А-Я][а-яё]+)*$", message = "Имя должно быть на русском языке, начинаться с большой буквы и иметь длину от 2 до 20 символов.") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Pattern(regexp = "^[А-Я][а-яё]+(?:-[А-Я][а-яё]+)*$", message = "Имя должно быть на русском языке, начинаться с большой буквы и иметь длину от 2 до 20 символов.") String firstName) {
        this.firstName = firstName;
    }

    public @Pattern(regexp = "^[А-Я][а-яё]+(?:-[А-Я][а-яё]+)*$", message = "Фамилия должнф быть на русском языке, начинаться с большой буквы и иметь длину от 2 до 20 символов.") String getLastName() {
        return lastName;
    }

    public void setLastName(@Pattern(regexp = "^[А-Я][а-яё]+(?:-[А-Я][а-яё]+)*$", message = "Фамилия должнф быть на русском языке, начинаться с большой буквы и иметь длину от 2 до 20 символов.") String lastName) {
        this.lastName = lastName;
    }

    public @NotEmpty(message = "Придумайте пароль!") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Придумайте пароль!") String password) {
        this.password = password;
    }

    public @Pattern(regexp = "^\\+7[0-9]{10}$", message = "Введите номер телефона без пробелов и иных разделителей, начиная с +7") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Pattern(regexp = "^\\+7[0-9]{10}$", message = "Введите номер телефона без пробелов и иных разделителей, начиная с +7") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Некорректный email!") String getEmail() {
        return email;
    }

    public void setEmail(@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Некорректный email!") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Придумайте секретный вопрос!") String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(@NotEmpty(message = "Придумайте секретный вопрос!") String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public @NotEmpty(message = "Заполните поле ответа на секретный вопрос!") String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(@NotEmpty(message = "Заполните поле ответа на секретный вопрос!") String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
