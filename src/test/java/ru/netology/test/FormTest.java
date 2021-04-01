package ru.netology.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.manager.DbManager;
import ru.netology.page.LoginPage;

import java.time.LocalDate;
import java.time.LocalDate.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FormTest {
    static final String cardNum1 = "4444 4444 4444 4441";
    LoginPage loginPage = new LoginPage();
    String year = Integer.toString(LocalDate.now().getYear() - 2000 + 2);

    @BeforeEach
    void setUp() {
        DbManager.clearTables();
        open("http://localhost:8080");
    }

    @Test
    void shouldMaxYear() {
        String maxYear = Integer.toString(LocalDate.now().getYear() - 2000 + 5);
        loginPage.validLogin(true, cardNum1
                , "12", maxYear, "Вася", "999", true);
    }

    @Test
    void shouldCVS999() {
        loginPage.validLogin(true, cardNum1
                , "12", year, "Вася", "999", true);
    }

    @Test
    void shouldCVS000() {
        loginPage.validLogin(true, cardNum1
                , "12", year, "Вася", "000", true);
    }

    @Test
    void shouldMonth01() {
        loginPage.validLogin(true, cardNum1
                , "01", year, "Вася", "456", true);
    }

    @Test
    void shouldMonth12() {
        loginPage.validLogin(true, cardNum1
                , "12", year, "Вася", "456", true);
    }

}
