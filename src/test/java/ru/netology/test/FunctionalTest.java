package ru.netology.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.manager.DbManager;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionalTest {

    static final String cardNum1 = "4444 4444 4444 4441";
    static final String cardNum2 = "4444 4444 4444 4442";
    static final String cardNum3 = "4444 4444 4444 4443";
    LoginPage loginPage = new LoginPage();

    @BeforeEach
    void setUp() {
        DbManager.clearTables();
        open("http://localhost:8080");
    }

    @Test
    void shouldErrorCardPayment() {
        String message = loginPage.validLogin(false, cardNum3
                , "02", "22", "Вася", "456", false);
        DbManager.checkRecordCount("order_entity",0);
        DbManager.checkRecordCount("payment_entity",0);
        DbManager.checkRecordCount("credit_request_entity",0);
    }

    @Test
    void shouldErrorCardCredit() {
        String message = loginPage.validLogin(true, cardNum3
                , "02", "22", "Вася", "456", false);
        DbManager.checkRecordCount("order_entity",0);
        DbManager.checkRecordCount("payment_entity",0);
        DbManager.checkRecordCount("credit_request_entity",0);
    }

    @Test
    void shouldHappyPathPayment() {
        String message = loginPage.validLogin(false, cardNum1
                , "02", "22", "Вася", "456", true);

        DbManager.checkRecordCount("order_entity",1);
        DbManager.checkRecordCount("payment_entity",1);
        DbManager.checkRecordCount("credit_request_entity",0);
        assertEquals(DbManager.getPayment().getTransaction_id()
                , DbManager.getOrder().getPayment_id());
        assertEquals("APPROVED",DbManager.getPayment().getStatus());
    }

    @Test
    void shouldHappyPathCredit() {
        String message = loginPage.validLogin(true, cardNum1
                , "02", "22", "Вася", "456", true);
        DbManager.checkRecordCount("order_entity",1);
        DbManager.checkRecordCount("credit_request_entity",1);
        DbManager.checkRecordCount("payment_entity",0);
        assertEquals(DbManager.getCredit().getBank_id()
                , DbManager.getOrder().getPayment_id());
        assertEquals("APPROVED",DbManager.getCredit().getStatus());
    }

    @Test
    void shouldDeclinedPayment() {
        String message = loginPage.validLogin(false, cardNum2
                , "02", "22", "Вася", "456", false);
        DbManager.checkRecordCount("order_entity", 0);
        DbManager.checkRecordCount("payment_entity", 0);
        DbManager.checkRecordCount("credit_request_entity", 0);
    }

    @Test
    void shouldDeclinedCredit() {
        String message = loginPage.validLogin(true, cardNum2
                , "02", "22", "Вася", "456", false);
        DbManager.checkRecordCount("order_entity", 0);
        DbManager.checkRecordCount("payment_entity", 0);
        DbManager.checkRecordCount("credit_request_entity", 0);
    }

}
