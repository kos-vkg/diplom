package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.manager.DbManager;
import ru.netology.page.LoginPage;

import java.time.LocalDate;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormTest {
    static final String cardNum1 = "4444 4444 4444 4441";
    LoginPage loginPage = new LoginPage();
    String year = Integer.toString(LocalDate.now().getYear() - 2000 + 2);

         //введение валидных данных и контроль результата
     private void dataCorrection(){
         loginPage.inputData(true, cardNum1
                 , "02", year, "Вася", "456");
         loginPage.notFieldErrors();
         loginPage.isSuccessMessage();
     }

    @BeforeEach
    void setUp() {
        DbManager.clearTables();
        open("http://localhost:8080");
    }

    @Test
    void shouldMaxYear() {
        String maxYear = Integer.toString(LocalDate.now().getYear() - 2000 + 5);
        loginPage.inputData(true, cardNum1
                , "12", maxYear, "Вася", "999");
        loginPage.notFieldErrors();
        loginPage.isSuccessMessage();
    }

    @Test
    void shouldCVS999() {
        loginPage.inputData(true, cardNum1
                , "12", year, "Вася", "999");
        loginPage.notFieldErrors();
        loginPage.isSuccessMessage();
    }

    @Test
    void shouldCVS000() {
        loginPage.inputData(true, cardNum1
                , "12", year, "Вася", "000");
        loginPage.notFieldErrors();
        loginPage.isSuccessMessage();
    }

    @Test
    void shouldMonth01() {
        loginPage.inputData(true, cardNum1
                , "01", year, "Вася", "456");
        loginPage.notFieldErrors();
        loginPage.isSuccessMessage();
    }

    @Test
    void shouldMonth12() {
        loginPage.inputData(true, cardNum1
                , "12", year, "Вася", "456");
        loginPage.notFieldErrors();
        loginPage.isSuccessMessage();
    }

    @Test
    void shouldMonth13() {
        loginPage.inputData(true, cardNum1
                , "13", year, "Вася", "456");
        loginPage.isFieldError("Неверно указан срок действия карты");
        dataCorrection(); //исправление ошибочных данных
    }

    @Test
    void shouldMonth00() {
        loginPage.inputData(true, cardNum1
                , "00", year, "Вася", "456");
        loginPage.isFieldError("Неверный формат");
        dataCorrection(); //исправление ошибочных данных
    }

    @Test
    void shouldMonth0() {
        loginPage.inputData(true, cardNum1
                , "0", year, "Вася", "456");
        loginPage.isFieldError("Неверный формат");
        dataCorrection(); //исправление ошибочных данных
    }

    @Test
    void shouldIncorrectCVS() {
        loginPage.inputData(true, cardNum1
                , "02", year, "Вася", "12");
        loginPage.isFieldError("Неверный формат");
        dataCorrection(); //исправление ошибочных данных
    }

    @Test
    void shouldCard15signs() {
        loginPage.inputData(true, "123456789012345"
                , "02", year, "Вася", "123");
        loginPage.isFieldError("Неверный формат");
        dataCorrection(); //исправление ошибочных данных
    }

    @Test
     void  shouldEmptyCard(){
         loginPage.inputData(true, ""
                 , "02", year, "Вася", "123");
         loginPage.isFieldError("Неверный формат");
         dataCorrection(); //исправление ошибочных данных
     }

    @Test
    void shouldEmptyMonth(){
        loginPage.inputData(true, cardNum1
                , "", year, "Вася", "123");
        loginPage.isFieldError("Неверный формат");
        dataCorrection(); //исправление ошибочных данных
    }

    @Test
    void shouldEmptyYear(){
        loginPage.inputData(true, cardNum1
                , "02", "", "Вася", "123");
        loginPage.isFieldError("Неверный формат");
        dataCorrection(); //исправление ошибочных данных
    }

    @Test
    void shouldEmptyCVS(){
        loginPage.inputData(true, cardNum1
                , "02", year, "Вася", "");
        loginPage.isFieldError("Поле обязательно для заполнения");
        dataCorrection(); //исправление ошибочных данных
    }

}
