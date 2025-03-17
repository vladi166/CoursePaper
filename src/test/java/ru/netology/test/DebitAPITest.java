package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.api.APIHelper;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataBaseHelper.cleanDB;

public class DebitAPITest {
    private final APIHelper apiHelper = new APIHelper();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setup() {
        cleanDB();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        DataBaseHelper.cleanDB();
    }

    // Проверка поля "Номер карты";

    //Номер карты со статусом: "APPROVED";
    @Test
    void shouldApprovePayment() {
        var validDebitCard = DataGenerator.FormPayment.getValidUser();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        var response = apiHelper.sendPostStatusSuccess(validDebitCard, pathForPay, statusCode);
        assertEquals(DataGenerator.getApprovedStatus(), response);
        assertEquals("APPROVED", DataBaseHelper.getDebitCardTransactionStatus());
    }

    //Номер карты со статусом: "DECLINED";
    @Test
    void shouldDeclinePayment() {
        var invalidDebitCard = DataGenerator.FormPayment.getDeclinedUser();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        var response = apiHelper.sendPostStatusSuccess(invalidDebitCard, pathForPay, statusCode);
        assertEquals(DataGenerator.getDeclinedStatus(), response);
        assertEquals("DECLINED", DataBaseHelper.getDebitCardTransactionStatus());
    }

    //Поле 'Номер карты' заполнено рандомным номером;
    @Test
    void clientErrorStatusSendAnyCardNumberForPay() {
        var infoHolderAnyCardNumber = DataGenerator.FormPayment.getAnyCardNumberUser();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoHolderAnyCardNumber, pathForPay, statusCode);
    }

    //Поле 'Номер карты' состоит из одного символа;
    @Test
    void clientErrorStatusSendCardWithPartNumberForPay() {
        var infoHolder = DataGenerator.FormPayment.getPartCardNumber();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoHolder, pathForPay, statusCode);
    }

    //Поле 'Номер карты' не должно принимать более 16 цифр
    @Test
    void clientErrorStatusSendMoreDigitsInCardNumberForPay() {
        var infoMoreDigitsInCardNumber = DataGenerator.FormPayment.getMoreDigitsInCardNumber();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoMoreDigitsInCardNumber, pathForPay, statusCode);
    }

    //буквы и цифры в поле
    @Test
    void cardNumberWithWordInNumber() {
        var infoCardNumberWithWordInNumber = DataGenerator.FormPayment.getCardNumberWithWordInNumber();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCardNumberWithWordInNumber, pathForPay, statusCode);
    }

    // поле состоит из букв
    @Test
    void cardNumberFieldConsistsLetters() {
        var infoCardNumberFieldConsistsLetters = DataGenerator.FormPayment.getCardNumberFieldConsistsOfLetters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCardNumberFieldConsistsLetters, pathForPay, statusCode);
    }

    // поле состоит из специальных символов
    @Test
    void cardNumberFieldWithSpecialCharacters() {
        var infoCardNumberFieldWithSpecialCharacters = DataGenerator.FormPayment.getCardNumberWithoutDigit();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCardNumberFieldWithSpecialCharacters, pathForPay, statusCode);
    }

    // пустое поле
    @Test
    void cardNumberFieldIsEmpty() {
        var infoCardNumberFieldIsEmpty = DataGenerator.FormPayment.getEmptyCardNumber();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCardNumberFieldIsEmpty, pathForPay, statusCode);
    }

    // Проверка поля "Месяц";

    //нули в поле
    @Test
    void useMonthDoubleZero() {
        var infoUseMonthDoubleZero = DataGenerator.FormPayment.getMonthDoubleZeroCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoUseMonthDoubleZero, pathForPay, statusCode);
    }

    //Поле 'Месяц' заполнено несуществующей датой;
    @Test
    void invalidMonthField() {
        var infoInvalidMonthField = DataGenerator.FormPayment.getMonthOverCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoInvalidMonthField, pathForPay, statusCode);
    }

    //Поле 'Месяц' состоит из одного символа;
    @Test
    void monthFieldConsistsOneCharacters() {
        var infoMonthFieldConsistsOneCharacters = DataGenerator.FormPayment.getOneDigitMonthCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoMonthFieldConsistsOneCharacters, pathForPay, statusCode);
    }

    //более 2 цифр
    @Test
    void useMoreDigitsInMonth() {
        var infoUseMoreDigitsInMonth = DataGenerator.FormPayment.getMoreDigitsInMonth();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoUseMoreDigitsInMonth, pathForPay, statusCode);
    }

    //пустое поле
    @Test
    void ifMonthFieldEmpty() {
        var infoIfMonthFieldEmpty = DataGenerator.FormPayment.getEmptyMonthFieldCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoIfMonthFieldEmpty, pathForPay, statusCode);
    }

    //Поле 'Месяц' состоит из букв
    @Test
    void monthFieldConsistsOfLetters() {
        var infoMonthFieldConsistsOfLetters = DataGenerator.FormPayment.getMonthFieldConsistsOfLetters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoMonthFieldConsistsOfLetters, pathForPay, statusCode);
    }

    //Поле 'Месяц' состоит из спецсимволов
    @Test
    void monthFieldWithSpecialCharacters() {
        var infoMonthFieldWithSpecialCharacters = DataGenerator.FormPayment.getMonthFieldWithSpecialCharacters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoMonthFieldWithSpecialCharacters, pathForPay, statusCode);
    }

    // Проверка поля "Год";

    // прошлый год
    @Test
    void pastYear() {
        var infoPastYear = DataGenerator.FormPayment.getPastYearCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoPastYear, pathForPay, statusCode);
    }

    //год, за пределами срока обслуживания карты
    @Test
    void futureYearOverCard() {
        var infoFutureYearOverCard = DataGenerator.FormPayment.getFutureYearOverCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoFutureYearOverCard, pathForPay, statusCode);
    }

    //последний год обслуживания карты
    @Test
    void ifLastYearExpirationDate() {
        var infoIfLastYearExpirationDate = DataGenerator.FormPayment.getLastYearExpirationDate();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        apiHelper.sendPostStatusSuccess(infoIfLastYearExpirationDate, pathForPay, statusCode);
        assertEquals("APPROVED", DataBaseHelper.getDebitCardTransactionStatus());
    }

    //Поле 'Год' состоит из одного символа
    @Test
    void shouldGetErrorIfYearFieldConsistsOneCharacters() {
        var infoShouldGetErrorIfYearFieldConsistsOneCharacters = DataGenerator.FormPayment.getOneDigitYearCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoShouldGetErrorIfYearFieldConsistsOneCharacters, pathForPay, statusCode);
    }

    //более 2 цифр
    @Test
    void inFieldUseMoreDigitsInYear() {
        var infoInFieldUseMoreDigitsInYear = DataGenerator.FormPayment.getMoreDigitsInYearCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoInFieldUseMoreDigitsInYear, pathForPay, statusCode);
    }

    // ввод букв
    @Test
    void inYearFieldConsistsOfLetters() {
        var infoInYearFieldConsistsOfLetters = DataGenerator.FormPayment.getYearFieldConsistsOfLetters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoInYearFieldConsistsOfLetters, pathForPay, statusCode);
    }

    //Поле 'Год' состоит из спецсимволов;
    @Test
    void yearFieldWithSpecialCharacters() {
        var infoYearFieldWithSpecialCharacters = DataGenerator.FormPayment.getYearFieldWithSpecialCharacters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoYearFieldWithSpecialCharacters, pathForPay, statusCode);
    }

    //Поле 'Год' пустое;
    @Test
    void getErrorIfYearFieldEmpty() {
        var infoGetErrorIfYearFieldEmpty = DataGenerator.FormPayment.getEmptyYearFieldCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoGetErrorIfYearFieldEmpty, pathForPay, statusCode);
    }

    // проверка на текущие месяц и год
    @Test
    void cardWithCurrentPeriod() {
        var infoCardWithCurrentPeriod = DataGenerator.FormPayment.getCardWithCurrentPeriod();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        apiHelper.sendPostStatusSuccess(infoCardWithCurrentPeriod, pathForPay, statusCode);
        assertEquals("APPROVED", DataBaseHelper.getDebitCardTransactionStatus());
    }

    // Проверка поля "Владелец";

    // поле на кириллице
    @Test
    void getErrorIfHolderFieldInCyrillic() {
        var infoGetErrorIfHolderFieldInCyrillic = DataGenerator.FormPayment.getCyrillicHolderCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoGetErrorIfHolderFieldInCyrillic, pathForPay, statusCode);
    }

    // ввод спецсимволов
    @Test
    void getErrorIfHolderFieldWithSpecialCharacters() {
        var infoGetErrorIfHolderFieldWithSpecialCharacters = DataGenerator.FormPayment.getSymbolHolderCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoGetErrorIfHolderFieldWithSpecialCharacters, pathForPay, statusCode);
    }

    // ввод букв и цифр
    @Test
    void useCardWithSymbolHolder() {
        var infoUseCardWithSymbolHolder = DataGenerator.FormPayment.getOwnerFieldWithLettersAndNumbers();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoUseCardWithSymbolHolder, pathForPay, statusCode);
    }

    // пустое поле
    @Test
    void fieldEmptyHolder() {
        var infoFieldEmptyHolder = DataGenerator.FormPayment.getEmptyHolderFieldCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoFieldEmptyHolder, pathForPay, statusCode);
    }

    // ввод одной буквы
    @Test
    void holderFieldConsistsOneCharacters() {
        var infoHolderFieldConsistsOneCharacters = DataGenerator.FormPayment.getOwnerFieldConsistsOneCharacters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoHolderFieldConsistsOneCharacters, pathForPay, statusCode);
    }

    // ввод двойной фамилии
    @Test
    void holderFieldWithDoubleSurname() {
        var infoHolderFieldWithDoubleSurname = DataGenerator.FormPayment.getOwnerFieldWithDoubleSurname();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        apiHelper.sendPostStatusSuccess(infoHolderFieldWithDoubleSurname, pathForPay, statusCode);
        assertEquals("APPROVED", DataBaseHelper.getDebitCardTransactionStatus());
    }

    // проверка на максимальный размер
    @Test
    void holderFieldWithMaxLength() {
        var infoHolderFieldWithMaxLength = DataGenerator.FormPayment.getOwnerFieldWithMaxLength();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoHolderFieldWithMaxLength, pathForPay, statusCode);
    }

    // Проверка поля "CVC"

    // ввод одной цифры
    @Test
    void fieldCVCConsistsOneCharacters() {
        var infoCVCFieldConsistsOneCharacters = DataGenerator.FormPayment.getOneDigitCvcCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCVCFieldConsistsOneCharacters, pathForPay, statusCode);
    }

    // ввод 3 цифр
    @Test
    void useMoreDigitsInCvc() {
        var infoUseMoreDigitsInCvc = DataGenerator.FormPayment.getMoreDigitsInCvcCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoUseMoreDigitsInCvc, pathForPay, statusCode);
    }

    // ввод спецсимволов
    @Test
    void fieldCVCWithSpecialCharacters() {
        var infoCVCFieldWithSpecialCharacters = DataGenerator.FormPayment.getCVCFieldWithSpecialCharacters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCVCFieldWithSpecialCharacters, pathForPay, statusCode);
    }

    // ввод букв
    @Test
    void fieldCVCConsistsOfLetters() {
        var infoCVCFieldConsistsOfLetters = DataGenerator.FormPayment.getCVCFieldConsistsOfLetters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCVCFieldConsistsOfLetters, pathForPay, statusCode);
    }

    // пустое поле
    @Test
    void fieldCVCEmpty() {
        var infoCVCFieldEmpty = DataGenerator.FormPayment.getEmptyCvcFieldCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCVCFieldEmpty, pathForPay, statusCode);
    }

    // пустая форма оплаты
    @Test
    void shouldShowErrorsForEmptyFields() {
        var infoShouldShowErrorsForEmptyFields = DataGenerator.FormPayment.getEmptyFormCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoShouldShowErrorsForEmptyFields, pathForPay, statusCode);
    }
}

