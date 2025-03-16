package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataGenerator;
import ru.netology.page.CreditCardPaymentPage;
import ru.netology.page.TitlePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardTest {

    private CreditCardPaymentPage creditCard;
    private TitlePage titlePage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setOpen() {
        titlePage = open("http://localhost:8080", TitlePage.class);
    }


    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        DataBaseHelper.cleanDB();
    }

    // Проверка поля "Номер карты";

    //Номер карты со статусом: "APPROVED";
    @Test
    void sendFormSuccessFully() {
        var creditCard = titlePage.creditCardPayment();
        var validCreditCard = DataGenerator.FormPayment.getValidUser();
        creditCard.validFillFieldCreditCard(validCreditCard);
        creditCard.successMessage();
        assertEquals("APPROVED", DataBaseHelper.getCreditCardTransactionStatus());
    }

    //Номер карты со статусом: "DECLINED";
    @Test
    void GetErrorIfInvalidCardNumber() {
        var creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getDeclinedUser();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessage();
        assertEquals("DECLINED", DataBaseHelper.getCreditCardTransactionStatus());
    }

    //Поле 'Номер карты' заполнено рандомным номером;
    @Test
    void shouldGetErrorIfAnyCardNumber() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getAnyCardNumberUser();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessage();
    }

    //Поле 'Номер карты' состоит из одного символа;
    @Test
    void shouldGetErrorIfCardNumberFieldConsistsOneCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getPartCardNumber();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    //Поле 'Номер карты' не должно принимать более 16 цифр
    @Test
    void UseMoreDigitsInCardNumber() {
        creditCard = titlePage.creditCardPayment();
        var cardNumber = DataGenerator.getRandomCardNumber();
        var digit = DataGenerator.getOneDigit();
        creditCard.setUpCardNumberField(cardNumber, digit);
    }

    //буквы и цифры в поле
    @Test
    void CardNumberWithWordInNumber() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getCardNumberWithWordInNumber();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // поле состоит из букв
    @Test
    void CardNumberFieldConsistsLetters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getCardNumberFieldConsistsOfLetters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // поле состоит из специальных символов
    @Test
    void CardNumberFieldWithSpecialCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getCardNumberWithoutDigit();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // пустое поле
    @Test
    void CardNumberFieldIsEmpty() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getEmptyCardNumber();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // Проверка поля "Месяц";

    //нули в поле
    @Test
    void UseMonthDoubleZero() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getMonthDoubleZeroCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageCardExpirationDateIncorrect();
    }

    //Поле 'Месяц' заполнено несуществующей датой;
    @Test
    void InvalidMonthField() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getMonthOverCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageCardExpirationDateIncorrect();
    }

    //Поле 'Месяц' состоит из одного символа;
    @Test
    void MonthFieldConsistsOneCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getOneDigitMonthCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    //более 2 цифр
    @Test
    void UseMoreDigitsInMonth() {
        creditCard = titlePage.creditCardPayment();
        var month = DataGenerator.getRandomMonth();
        var digit = DataGenerator.getOneDigit();
        creditCard.setUpMonthField(month, digit);
    }

    //пустое поле
    @Test
    void IfMonthFieldEmpty() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getEmptyMonthFieldCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    //Поле 'Месяц' состоит из букв
    @Test
    void MonthFieldConsistsOfLetters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getMonthFieldConsistsOfLetters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    //Поле 'Месяц' состоит из спецсимволов
    @Test
    void MonthFieldWithSpecialCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getMonthFieldWithSpecialCharacters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // Проверка поля "Год";

    // прошлый год
    @Test
    void PastYear() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getPastYearCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageByIncorrectFormatYearField();
    }

    //год, за пределами срока обслуживания карты
    @Test
    void FutureYearOverCard() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getFutureYearOverCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageCardExpirationDateIncorrect();
    }

    //последний год обслуживания карты
    @Test
    void IfLastYearExpirationDate() {
        creditCard = titlePage.creditCardPayment();
        var validCreditCard = DataGenerator.FormPayment.getLastYearExpirationDate();
        creditCard.validFillFieldCreditCard(validCreditCard);
        creditCard.successMessage();
        assertEquals("APPROVED", DataBaseHelper.getCreditCardTransactionStatus());
    }

    //Поле 'Год' состоит из одного символа
    @Test
    void shouldGetErrorIfYearFieldConsistsOneCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getOneDigitYearCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    //более 2 цифр
    @Test
    void InFieldUseMoreDigitsInYear() {
        creditCard = titlePage.creditCardPayment();
        var year = DataGenerator.getYearFutureInPeriod();
        var digit = DataGenerator.getOneDigit();
        creditCard.setUpYearField(year, digit);
    }

    // ввод букв
    @Test
    void InYearFieldConsistsOfLetters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getYearFieldConsistsOfLetters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    //Поле 'Год' состоит из спецсимволов;
    @Test
    void YearFieldWithSpecialCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getYearFieldWithSpecialCharacters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    //Поле 'Год' пустое;
    @Test
    void GetErrorIfYearFieldEmpty() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getEmptyYearFieldCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // проверка на текущие месяц и год
    @Test
    void CardWithCurrentPeriod() {
        creditCard = titlePage.creditCardPayment();
        var validCreditCard = DataGenerator.FormPayment.getCardWithCurrentPeriod();
        creditCard.validFillFieldCreditCard(validCreditCard);
        creditCard.successMessage();
        assertEquals("APPROVED", DataBaseHelper.getCreditCardTransactionStatus());
    }

    // Проверка поля "Владелец";

    // поле на кириллице
    @Test
    void GetErrorIfHolderFieldInCyrillic() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getCyrillicHolderCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // ввод спецсимволов
    @Test
    void GetErrorIfHolderFieldWithSpecialCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getSymbolHolderCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // ввод букв и цифр
    @Test
    void UseCardWithSymbolHolder() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getOwnerFieldWithLettersAndNumbers();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // пустое поле
    @Test
    void FieldEmptyHolder() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getEmptyHolderFieldCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageEmptyField();
    }

    // ввод одной буквы
    @Test
    void HolderFieldConsistsOneCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getOwnerFieldConsistsOneCharacters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // ввод двойной фамилии
    @Test
    void HolderFieldWithDoubleSurname() {
        creditCard = titlePage.creditCardPayment();
        var validCreditCard = DataGenerator.FormPayment.getOwnerFieldWithDoubleSurname();
        creditCard.validFillFieldCreditCard(validCreditCard);
        creditCard.successMessage();
        assertEquals("APPROVED", DataBaseHelper.getCreditCardTransactionStatus());
    }

    // проверка на максимальный размер
    @Test
    void HolderFieldWithMaxLength() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getOwnerFieldWithMaxLength();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // Проверка поля "CVC"

    // ввод одной цифры
    @Test
    void CVCFieldConsistsOneCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getOneDigitCvcCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // ввод 3 цифр
    @Test
    void UseMoreDigitsInCvc() {
        creditCard = titlePage.creditCardPayment();
        var cvc = DataGenerator.getCVC();
        var digit = DataGenerator.getOneDigit();
        creditCard.setUpCvcField(cvc, digit);
    }

    // ввод спецсимволов
    @Test
    void CVCFieldWithSpecialCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getCVCFieldWithSpecialCharacters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // ввод букв
    @Test
    void CVCFieldConsistsOfLetters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getCVCFieldConsistsOfLetters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // пустое поле
    @Test
    void CVCFieldEmpty() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getEmptyCvcFieldCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // пустая форма оплаты
    @Test
    void shouldShowErrorsForEmptyFields() {
        creditCard = titlePage.creditCardPayment();
        var emptyForm = DataGenerator.FormPayment.getEmptyFormCard();
        creditCard.invalidFillFieldCreditCard(emptyForm);
        creditCard.errorMessageEmptyField();
        creditCard.errorMessageIncorrectFormat();
    }
}

