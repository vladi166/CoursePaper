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
import static ru.netology.data.DataBaseHelper.cleanDB;

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

    @BeforeEach
    void setup() {
        cleanDB();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        cleanDB();
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
    void getErrorIfInvalidCardNumber() {
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
    void useMoreDigitsInCardNumber() {
        creditCard = titlePage.creditCardPayment();
        var cardNumber = DataGenerator.getRandomCardNumber();
        var digit = DataGenerator.getOneDigit();
        creditCard.setUpCardNumberField(cardNumber, digit);
    }

    //буквы и цифры в поле
    @Test
    void cardNumberWithWordInNumber() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getCardNumberWithWordInNumber();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // поле состоит из букв
    @Test
    void cardNumberFieldConsistsLetters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getCardNumberFieldConsistsOfLetters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // поле состоит из специальных символов
    @Test
    void cardNumberFieldWithSpecialCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getCardNumberWithoutDigit();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // пустое поле
    @Test
    void cardNumberFieldIsEmpty() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getEmptyCardNumber();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // Проверка поля "Месяц";

    //нули в поле
    @Test
    void useMonthDoubleZero() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getMonthDoubleZeroCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageCardExpirationDateIncorrect();
    }

    //Поле 'Месяц' заполнено несуществующей датой;
    @Test
    void invalidMonthField() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getMonthOverCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageCardExpirationDateIncorrect();
    }

    //Поле 'Месяц' состоит из одного символа;
    @Test
    void monthFieldConsistsOneCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getOneDigitMonthCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    //более 2 цифр
    @Test
    void useMoreDigitsInMonth() {
        creditCard = titlePage.creditCardPayment();
        var month = DataGenerator.getRandomMonth();
        var digit = DataGenerator.getOneDigit();
        creditCard.setUpMonthField(month, digit);
    }

    //пустое поле
    @Test
    void ifMonthFieldEmpty() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getEmptyMonthFieldCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    //Поле 'Месяц' состоит из букв
    @Test
    void monthFieldConsistsOfLetters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getMonthFieldConsistsOfLetters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    //Поле 'Месяц' состоит из спецсимволов
    @Test
    void monthFieldWithSpecialCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getMonthFieldWithSpecialCharacters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // Проверка поля "Год";

    // прошлый год
    @Test
    void pastYear() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getPastYearCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageByIncorrectFormatYearField();
    }

    //год, за пределами срока обслуживания карты
    @Test
    void futureYearOverCard() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getFutureYearOverCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageCardExpirationDateIncorrect();
    }

    //последний год обслуживания карты
    @Test
    void ifLastYearExpirationDate() {
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
    void inFieldUseMoreDigitsInYear() {
        creditCard = titlePage.creditCardPayment();
        var year = DataGenerator.getYearFutureInPeriod();
        var digit = DataGenerator.getOneDigit();
        creditCard.setUpYearField(year, digit);
    }

    // ввод букв
    @Test
    void inYearFieldConsistsOfLetters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getYearFieldConsistsOfLetters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    //Поле 'Год' состоит из спецсимволов;
    @Test
    void yearFieldWithSpecialCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getYearFieldWithSpecialCharacters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    //Поле 'Год' пустое;
    @Test
    void getErrorIfYearFieldEmpty() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getEmptyYearFieldCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // проверка на текущие месяц и год
    @Test
    void cardWithCurrentPeriod() {
        creditCard = titlePage.creditCardPayment();
        var validCreditCard = DataGenerator.FormPayment.getCardWithCurrentPeriod();
        creditCard.validFillFieldCreditCard(validCreditCard);
        creditCard.successMessage();
        assertEquals("APPROVED", DataBaseHelper.getCreditCardTransactionStatus());
    }

    // Проверка поля "Владелец";

    // поле на кириллице
    @Test
    void getErrorIfHolderFieldInCyrillic() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getCyrillicHolderCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // ввод спецсимволов
    @Test
    void getErrorIfHolderFieldWithSpecialCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getSymbolHolderCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // ввод букв и цифр
    @Test
    void useCardWithSymbolHolder() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getOwnerFieldWithLettersAndNumbers();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // пустое поле
    @Test
    void fieldEmptyHolder() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getEmptyHolderFieldCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageEmptyField();
    }

    // ввод одной буквы
    @Test
    void holderFieldConsistsOneCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getOwnerFieldConsistsOneCharacters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // ввод двойной фамилии
    @Test
    void holderFieldWithDoubleSurname() {
        creditCard = titlePage.creditCardPayment();
        var validCreditCard = DataGenerator.FormPayment.getOwnerFieldWithDoubleSurname();
        creditCard.validFillFieldCreditCard(validCreditCard);
        creditCard.successMessage();
        assertEquals("APPROVED", DataBaseHelper.getCreditCardTransactionStatus());
    }

    // проверка на максимальный размер
    @Test
    void holderFieldWithMaxLength() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getOwnerFieldWithMaxLength();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // Проверка поля "CVC"

    // ввод одной цифры
    @Test
    void fieldCVCConsistsOneCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getOneDigitCvcCard();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // ввод 3 цифр
    @Test
    void useMoreDigitsInCvc() {
        creditCard = titlePage.creditCardPayment();
        var cvc = DataGenerator.getCVC();
        var digit = DataGenerator.getOneDigit();
        creditCard.setUpCvcField(cvc, digit);
    }

    // ввод спецсимволов
    @Test
    void fieldCVCWithSpecialCharacters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getCVCFieldWithSpecialCharacters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // ввод букв
    @Test
    void fieldCVCConsistsOfLetters() {
        creditCard = titlePage.creditCardPayment();
        var invalidCreditCard = DataGenerator.FormPayment.getCVCFieldConsistsOfLetters();
        creditCard.invalidFillFieldCreditCard(invalidCreditCard);
        creditCard.errorMessageIncorrectFormat();
    }

    // пустое поле
    @Test
    void fieldCVCEmpty() {
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

