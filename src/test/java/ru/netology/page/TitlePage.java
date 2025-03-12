package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TitlePage {
    private final SelenideElement heading = $(".heading.heading_size_l.heading_theme_alfa-on-white");
    private final SelenideElement debitCardButton = $x("//button[.//span[text()='Купить']]");
    private final SelenideElement creditCardButton = $x("//button[.//span[text()='Купить в кредит']]");

    public String getHeadingText() {
        return heading.getText();
    }

    public DebitCardPaymentPage debitCardPayment() {
        debitCardButton.click();
        return new DebitCardPaymentPage();
    }

    public CreditCardPaymentPage creditCardPayment() {
        creditCardButton.click();
        return new CreditCardPaymentPage();
    }
}
