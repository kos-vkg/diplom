package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPage {

    static final String MESS_OK = "Операция одобрена Банком.";
    static final String MESS_ERR = "Ошибка! Банк отказал в проведении операции.";
    static final String CLEAR_CODE = "\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b";

    private SelenideElement paymentButton = $$(".button_size_m").first();
    private SelenideElement creditButton = $$(".button_view_extra").first();
    private SelenideElement nextButton = $(".form-field .button");
    private SelenideElement messageButton = $(".notification__content");
    private SelenideElement cardField = $("[placeHolder='0000 0000 0000 0000']");
    private SelenideElement monthField = $$("[tabindex='-1'] input").get(0); // $("[placeHolder='08'");
    private SelenideElement yearField = $$("[tabindex='-1'] input").get(1);  // $("[placeHolder='22'");
    private SelenideElement nameField = $$("[tabindex='-1'] input").get(2);
    private SelenideElement cvsField = $$("[tabindex='-1'] input").get(3);  // $("[placeHolder='999'");
    private SelenideElement errorMessage = $(withText(MESS_ERR));
    private SelenideElement successMessage = $(withText(MESS_OK));
    private SelenideElement errorField = $(".input__sub");

    public String getMessage(SelenideElement message) {
        message.shouldBe(visible, Duration.ofSeconds(12));
        String text = message.getText();
        return text;
    }

    public String isErrorMessage() {
        String actualMessage = getMessage(errorMessage);
        notSuccessMessage();
        return actualMessage;
    }

    public void notErrorMessage() {
        errorMessage.shouldNotBe(visible);
    }

    public String isSuccessMessage() {
        String actualMessage = getMessage(successMessage);
        notErrorMessage();
        return actualMessage;
    }

    public void notSuccessMessage() {
        successMessage.shouldNotBe(visible);
    }

    public String isFieldError(String expectedMessage) {
        String actualMessage = getMessage(errorField);
        assertEquals(expectedMessage, actualMessage);
        return actualMessage;
    }

    public void notFieldErrors() {
        errorField.shouldNotBe(visible);
    }

    public void inputData(Boolean credit, String cardNum, String month
            , String year, String name, String cvs) {
        if (credit) {
            creditButton.click();
        } else {
            paymentButton.click();
        }
        nextButton.shouldBe(visible);
        cardField.setValue(CLEAR_CODE + cardNum);
        monthField.setValue(CLEAR_CODE + month);
        yearField.setValue(CLEAR_CODE + year);
        nameField.setValue(CLEAR_CODE + name);
        cvsField.setValue(CLEAR_CODE + cvs);
        nextButton.click();
    }

}
