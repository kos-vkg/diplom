package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.manager.DbManager;
import ru.netology.page.LoginPage;
import io.qameta.allure.selenide.AllureSelenide;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionalTest {

    static final String CARD_NUM_1 = "4444 4444 4444 4441";
    static final String CARD_NUM_2 = "4444 4444 4444 4442";
    static final String CARD_NUM_3 = "4444 4444 4444 4443";
    LoginPage loginPage = new LoginPage();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @BeforeEach
    void setUp() {
        DbManager.clearTables();
        open("http://localhost:8080");
    }

    @Test
    void shouldErrorCardPayment() {
        loginPage.inputData(false, CARD_NUM_3
                , "02", "22", "Вася", "456");
        loginPage.isErrorMessage();
        DbManager.checkRecordCount("order_entity", 0);
        DbManager.checkRecordCount("payment_entity", 0);
        DbManager.checkRecordCount("credit_request_entity", 0);
    }

    @Test
    void shouldErrorCardCredit() {
        loginPage.inputData(true, CARD_NUM_3
                , "02", "22", "Вася", "456");
        loginPage.isErrorMessage();
        DbManager.checkRecordCount("order_entity", 0);
        DbManager.checkRecordCount("payment_entity", 0);
        DbManager.checkRecordCount("credit_request_entity", 0);
    }

    @Test
    void shouldHappyPathPayment() {
        loginPage.inputData(false, CARD_NUM_1
                , "02", "22", "Вася", "456");
        loginPage.isSuccessMessage();
        DbManager.checkRecordCount("order_entity", 1);
        DbManager.checkRecordCount("payment_entity", 1);
        DbManager.checkRecordCount("credit_request_entity", 0);
        assertEquals(DbManager.getPayment().getTransaction_id()
                , DbManager.getOrder().getPayment_id());
        assertEquals("APPROVED", DbManager.getPayment().getStatus());
    }

    @Test
    void shouldHappyPathCredit() {
        loginPage.inputData(true, CARD_NUM_1
                , "02", "22", "Вася", "456");
        loginPage.isSuccessMessage();
        DbManager.checkRecordCount("order_entity", 1);
        DbManager.checkRecordCount("credit_request_entity", 1);
        DbManager.checkRecordCount("payment_entity", 0);
        assertEquals(DbManager.getCredit().getBank_id()
                , DbManager.getOrder().getPayment_id());
        assertEquals("APPROVED", DbManager.getCredit().getStatus());
    }

    @Test
    void shouldDeclinedPayment() {
        loginPage.inputData(false, CARD_NUM_2
                , "02", "22", "Вася", "456");
        loginPage.isErrorMessage();
        DbManager.checkRecordCount("order_entity", 0);
        DbManager.checkRecordCount("payment_entity", 0);
        DbManager.checkRecordCount("credit_request_entity", 0);
    }

    @Test
    void shouldDeclinedCredit() {
        loginPage.inputData(true, CARD_NUM_2
                , "02", "22", "Вася", "456");
        loginPage.isErrorMessage();
        DbManager.checkRecordCount("order_entity", 0);
        DbManager.checkRecordCount("payment_entity", 0);
        DbManager.checkRecordCount("credit_request_entity", 0);
    }

}
