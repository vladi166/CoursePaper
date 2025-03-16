package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataGenerator;
import ru.netology.page.DebitCardPaymentPage;
import ru.netology.page.TitlePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitCardTest {

    private DebitCardPaymentPage debitCard;
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
        var debitCard = titlePage.debitCardPayment();
        var validDebitCard = DataGenerator.FormPayment.getValidUser();
        debitCard.validFillFieldDebitCard(validDebitCard);
        debitCard.successMessage();
        assertEquals("APPROVED", DataBaseHelper.getDebitCardTransactionStatus());
    }

    //Номер карты со статусом: "DECLINED";
    @Test
    void GetErrorIfInvalidCardNumber() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getDeclinedUser();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessage();
        assertEquals("DECLINED", DataBaseHelper.getDebitCardTransactionStatus());
    }

    //Поле 'Номер карты' заполнено рандомным номером;
    @Test
    void shouldGetErrorIfAnyCardNumber() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getAnyCardNumberUser();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessage();
    }

    //Поле 'Номер карты' состоит из одного символа;
    @Test
    void shouldGetErrorIfCardNumberFieldConsistsOneCharacters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getPartCardNumber();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    //Поле 'Номер карты' не должно принимать более 16 цифр
    @Test
    void UseMoreDigitsInCardNumber() {
        debitCard = titlePage.debitCardPayment();
        var cardNumber = DataGenerator.getRandomCardNumber();
        var digit = DataGenerator.getOneDigit();
        debitCard.setUpCardNumberField(cardNumber, digit);
    }

    //буквы и цифры в поле
    @Test
    void CardNumberWithWordInNumber() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getCardNumberWithWordInNumber();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // поле состоит из букв
    @Test
    void CardNumberFieldConsistsLetters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getCardNumberFieldConsistsOfLetters();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // поле состоит из специальных символов
    @Test
    void CardNumberFieldWithSpecialCharacters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getCardNumberWithoutDigit();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // пустое поле
    @Test
    void CardNumberFieldIsEmpty() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getEmptyCardNumber();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // Проверка поля "Месяц";

    //нули в поле
    @Test
    void UseMonthDoubleZero() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getMonthDoubleZeroCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageCardExpirationDateIncorrect();
    }

    //Поле 'Месяц' заполнено несуществующей датой;
    @Test
    void InvalidMonthField() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getMonthOverCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageCardExpirationDateIncorrect();
    }

    //Поле 'Месяц' состоит из одного символа;
    @Test
    void MonthFieldConsistsOneCharacters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getOneDigitMonthCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    //более 2 цифр
    @Test
    void UseMoreDigitsInMonth() {
        debitCard = titlePage.debitCardPayment();
        var month = DataGenerator.getRandomMonth();
        var digit = DataGenerator.getOneDigit();
        debitCard.setUpMonthField(month, digit);
    }

    //пустое поле
    @Test
    void IfMonthFieldEmpty() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getEmptyMonthFieldCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    //Поле 'Месяц' состоит из букв
    @Test
    void MonthFieldConsistsOfLetters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getMonthFieldConsistsOfLetters();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    //Поле 'Месяц' состоит из спецсимволов
    @Test
    void MonthFieldWithSpecialCharacters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getMonthFieldWithSpecialCharacters();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // Проверка поля "Год";

    // прошлый год
    @Test
    void PastYear() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getPastYearCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageByIncorrectFormatYearField();
    }

    //год, за пределами срока обслуживания карты
    @Test
    void FutureYearOverCard() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getFutureYearOverCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageCardExpirationDateIncorrect();
    }

    //последний год обслуживания карты
    @Test
    void IfLastYearExpirationDate() {
        debitCard = titlePage.debitCardPayment();
        var validDebitCard = DataGenerator.FormPayment.getLastYearExpirationDate();
        debitCard.validFillFieldDebitCard(validDebitCard);
        debitCard.successMessage();
        assertEquals("APPROVED", DataBaseHelper.getDebitCardTransactionStatus());
    }

    //Поле 'Год' состоит из одного символа
    @Test
    void shouldGetErrorIfYearFieldConsistsOneCharacters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getOneDigitYearCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    //более 2 цифр
    @Test
    void InFieldUseMoreDigitsInYear() {
        debitCard = titlePage.debitCardPayment();
        var year = DataGenerator.getYearFutureInPeriod();
        var digit = DataGenerator.getOneDigit();
        debitCard.setUpYearField(year, digit);
    }

    // ввод букв
    @Test
    void InYearFieldConsistsOfLetters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getYearFieldConsistsOfLetters();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    //Поле 'Год' состоит из спецсимволов;
    @Test
    void YearFieldWithSpecialCharacters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getYearFieldWithSpecialCharacters();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    //Поле 'Год' пустое;
    @Test
    void GetErrorIfYearFieldEmpty() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getEmptyYearFieldCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // проверка на текущие месяц и год
    @Test
    void CardWithCurrentPeriod() {
        debitCard = titlePage.debitCardPayment();
        var validDebitCard = DataGenerator.FormPayment.getCardWithCurrentPeriod();
        debitCard.validFillFieldDebitCard(validDebitCard);
        debitCard.successMessage();
        assertEquals("APPROVED", DataBaseHelper.getDebitCardTransactionStatus());
    }

    // Проверка поля "Владелец";

    // поле на кириллице
    @Test
    void GetErrorIfHolderFieldInCyrillic() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getCyrillicHolderCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // ввод спецсимволов
    @Test
    void GetErrorIfHolderFieldWithSpecialCharacters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getSymbolHolderCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // ввод букв и цифр
    @Test
    void UseCardWithSymbolHolder() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getOwnerFieldWithLettersAndNumbers();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // пустое поле
    @Test
    void FieldEmptyHolder() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getEmptyHolderFieldCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageEmptyField();
    }

    // ввод одной буквы
    @Test
    void HolderFieldConsistsOneCharacters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getOwnerFieldConsistsOneCharacters();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // ввод двойной фамилии
    @Test
    void HolderFieldWithDoubleSurname() {
        debitCard = titlePage.debitCardPayment();
        var validDebitCard = DataGenerator.FormPayment.getOwnerFieldWithDoubleSurname();
        debitCard.validFillFieldDebitCard(validDebitCard);
        debitCard.successMessage();
        assertEquals("APPROVED", DataBaseHelper.getDebitCardTransactionStatus());
    }

    // проверка на максимальный размер
    @Test
    void HolderFieldWithMaxLength() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getOwnerFieldWithMaxLength();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // Проверка поля "CVC"

    // ввод одной цифры
    @Test
    void CVCFieldConsistsOneCharacters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getOneDigitCvcCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // ввод 3 цифр
    @Test
    void UseMoreDigitsInCvc() {
        debitCard = titlePage.debitCardPayment();
        var cvc = DataGenerator.getCVC();
        var digit = DataGenerator.getOneDigit();
        debitCard.setUpCvcField(cvc, digit);
    }

    // ввод спецсимволов
    @Test
    void CVCFieldWithSpecialCharacters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getCVCFieldWithSpecialCharacters();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // ввод букв
    @Test
    void CVCFieldConsistsOfLetters() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getCVCFieldConsistsOfLetters();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // пустое поле
    @Test
    void CVCFieldEmpty() {
        debitCard = titlePage.debitCardPayment();
        var invalidDebitCard = DataGenerator.FormPayment.getEmptyCvcFieldCard();
        debitCard.invalidFillFieldDebitCard(invalidDebitCard);
        debitCard.errorMessageIncorrectFormat();
    }

    // пустая форма оплаты
    @Test
    void shouldShowErrorsForEmptyFields() {
        debitCard = titlePage.debitCardPayment();
        var emptyForm = DataGenerator.FormPayment.getEmptyFormCard();
        debitCard.validFillFieldDebitCard(emptyForm);
        debitCard.errorMessageEmptyField();
        debitCard.errorMessageIncorrectFormat();
    }
}
