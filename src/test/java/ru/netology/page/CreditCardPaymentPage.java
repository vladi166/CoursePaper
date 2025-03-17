package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CreditCardPaymentPage {
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement holderField = $x("//span[@class='input__top' and text()='Владелец']/following-sibling::span[@class='input__box']//input");
    private SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement buttonCont = $x(".//span[text()='Продолжить']//ancestor::button");

    public CreditCardPaymentPage() {
        $x("//h3[text()='Кредит по данным карты']").shouldBe(visible);
    }

    //    Успешная отправка формы;
    public void validFillFieldCreditCard(DataGenerator.AuthInfo info) {
        cardNumberField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        holderField.setValue(info.getHolder());
        cvcField.setValue(info.getCvc());
        buttonCont.click();
    }

    //    Ошибка при отправке формы;
    public void invalidFillFieldCreditCard(DataGenerator.AuthInfo info) {
        cardNumberField.clear();
        cardNumberField.setValue(info.getNumber());
        monthField.clear();
        monthField.setValue(info.getMonth());
        yearField.clear();
        yearField.setValue(info.getYear());
        holderField.clear();
        holderField.setValue(info.getHolder());
        cvcField.clear();
        cvcField.setValue(info.getCvc());
        buttonCont.click();
    }

    public void successMessage() {
        $(".notification_status_ok").shouldBe(visible, Duration.ofSeconds(10));
    }

    public void errorMessage() {
        $(".notification_status_error").shouldBe(visible, Duration.ofSeconds(10));
    }

    public void errorMessageIncorrectFormat() {
        $(byText("Неверный формат")).shouldBe(Condition.visible);
    }

    public void errorMessageEmptyField() {
        $(byText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    public void errorMessageCardExpirationDateIncorrect() {
        $(byText("Неверно указан срок действия карты")).shouldBe(Condition.visible);
    }

    public void errorMessageByIncorrectFormatYearField() {
        $(byText("Истёк срок действия карты")).shouldBe(Condition.visible);
    }

    public void setUpCardNumberField(String number, String digit) {
        cardNumberField.setValue(number + digit);
        cardNumberField.shouldHave(value(number));
    }

    public void setUpMonthField(String month, String digit) {
        monthField.setValue(month + digit);
        monthField.shouldHave(value(month));
    }

    public void setUpYearField(String year, String digit) {
        yearField.setValue(year + digit);
        yearField.shouldHave(value(year));
    }

    public void setUpCvcField(String cvc, String digit) {
        cvcField.setValue(cvc + digit);
        cvcField.shouldHave(value(cvc));
    }
}
