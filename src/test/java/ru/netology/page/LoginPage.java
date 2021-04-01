package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPage {

    static final String messOk = "Операция одобрена Банком.";
    static final String messErr = "Ошибка! Банк отказал в проведении операции.";

    private SelenideElement paymentButton = $$(".button_size_m").first();
    private SelenideElement creditButton = $$(".button_view_extra").first();
    private SelenideElement nextButton = $(".form-field .button");
    private SelenideElement messageButton = $(".notification__content");
    private SelenideElement cardField = $("[placeHolder='0000 0000 0000 0000']");
    private SelenideElement monthField = $$("[tabindex='-1'] input").get(0); // $("[placeHolder='08'");
    private SelenideElement yearField = $$("[tabindex='-1'] input").get(1);  // $("[placeHolder='22'");
    private SelenideElement nameField = $$("[tabindex='-1'] input").get(2);
    private SelenideElement cvsField = $$("[tabindex='-1'] input").get(3);  // $("[placeHolder='999'");
    private SelenideElement errorMessage = $$(".notification__content").last();
    private SelenideElement successMessage = $(".notification__content");


    public String getMessage(SelenideElement message) {
        message.shouldBe(visible, Duration.ofSeconds(12));
        String text = message.getText();
        return text;
    }

        //ввод данных в форму и проверка сообщения об успешной регистрации
        //при параметре expected = true  или ошибке при expected = false
    public String validLogin(Boolean credit, String cardNum, String month
            , String year, String name, String cvs, boolean expected) {
        if (credit) {
            creditButton.click();
        } else {
            paymentButton.click();
        }
        nextButton.shouldBe(visible);
        cardField.setValue(cardNum);
        monthField.setValue(month);
        yearField.setValue(year);
        nameField.setValue(name);
        cvsField.setValue(cvs);
        nextButton.click();

        String actualMessage;
        String expectedMessage;
        if (expected) {
            actualMessage = getMessage(successMessage);
            expectedMessage = messOk;
        } else {
            actualMessage = getMessage(errorMessage);
            expectedMessage = messErr;
        }
       System.out.println(actualMessage);
        assertEquals(expectedMessage, actualMessage);
        return actualMessage;
    }

}
