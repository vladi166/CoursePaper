package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.api.APIHelper;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditAPITest {
    private final APIHelper apiHelper = new APIHelper();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
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
        var validCreditCard = DataGenerator.FormPayment.getValidUser();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        var response = apiHelper.sendPostStatusSuccess(validCreditCard, pathForCredit, statusCode);
        assertEquals(DataGenerator.getApprovedStatus(), response);
    }

    //Номер карты со статусом: "DECLINED";
    @Test
    void shouldDeclinePayment() {
        var invalidCreditCard = DataGenerator.FormPayment.getDeclinedUser();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        var response = apiHelper.sendPostStatusSuccess(invalidCreditCard, pathForCredit, statusCode);
        assertEquals(DataGenerator.getDeclinedStatus(), response);
    }

    //Поле 'Номер карты' заполнено рандомным номером;
    @Test
    void clientErrorStatusSendAnyCardNumberForPay() {
        var infoHolderAnyCardNumber = DataGenerator.FormPayment.getAnyCardNumberUser();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoHolderAnyCardNumber, pathForCredit, statusCode);
    }

    //Поле 'Номер карты' состоит из одного символа;
    @Test
    void ClientErrorStatusSendCardWithPartNumberForPay() {
        var infoHolder = DataGenerator.FormPayment.getPartCardNumber();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoHolder, pathForCredit, statusCode);
    }

    //Поле 'Номер карты' не должно принимать более 16 цифр
    @Test
    void ClientErrorStatusSendMoreDigitsInCardNumberForPay() {
        var infoMoreDigitsInCardNumber = DataGenerator.FormPayment.getMoreDigitsInCardNumber();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoMoreDigitsInCardNumber, pathForCredit, statusCode);
    }

    //буквы и цифры в поле
    @Test
    void CardNumberWithWordInNumber() {
        var infoCardNumberWithWordInNumber = DataGenerator.FormPayment.getCardNumberWithWordInNumber();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCardNumberWithWordInNumber, pathForCredit, statusCode);
    }

    // поле состоит из букв
    @Test
    void CardNumberFieldConsistsLetters() {
        var infoCardNumberFieldConsistsLetters = DataGenerator.FormPayment.getCardNumberFieldConsistsOfLetters();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCardNumberFieldConsistsLetters, pathForCredit, statusCode);
    }

    // поле состоит из специальных символов
    @Test
    void CardNumberFieldWithSpecialCharacters() {
        var infoCardNumberFieldWithSpecialCharacters = DataGenerator.FormPayment.getCardNumberWithoutDigit();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCardNumberFieldWithSpecialCharacters, pathForCredit, statusCode);
    }

    // пустое поле
    @Test
    void CardNumberFieldIsEmpty() {
        var infoCardNumberFieldIsEmpty = DataGenerator.FormPayment.getEmptyCardNumber();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCardNumberFieldIsEmpty, pathForCredit, statusCode);
    }

    // Проверка поля "Месяц";

    //нули в поле
    @Test
    void UseMonthDoubleZero() {
        var infoUseMonthDoubleZero = DataGenerator.FormPayment.getMonthDoubleZeroCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoUseMonthDoubleZero, pathForCredit, statusCode);
    }

    //Поле 'Месяц' заполнено несуществующей датой;
    @Test
    void InvalidMonthField() {
        var infoInvalidMonthField = DataGenerator.FormPayment.getMonthOverCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoInvalidMonthField, pathForCredit, statusCode);
    }

    //Поле 'Месяц' состоит из одного символа;
    @Test
    void MonthFieldConsistsOneCharacters() {
        var infoMonthFieldConsistsOneCharacters = DataGenerator.FormPayment.getOneDigitMonthCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoMonthFieldConsistsOneCharacters, pathForCredit, statusCode);
    }

    //более 2 цифр
    @Test
    void UseMoreDigitsInMonth() {
        var infoUseMoreDigitsInMonth = DataGenerator.FormPayment.getMoreDigitsInMonth();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoUseMoreDigitsInMonth, pathForCredit, statusCode);
    }

    //пустое поле
    @Test
    void IfMonthFieldEmpty() {
        var infoIfMonthFieldEmpty = DataGenerator.FormPayment.getEmptyMonthFieldCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoIfMonthFieldEmpty, pathForCredit, statusCode);
    }

    //Поле 'Месяц' состоит из букв
    @Test
    void MonthFieldConsistsOfLetters() {
        var infoMonthFieldConsistsOfLetters = DataGenerator.FormPayment.getMonthFieldConsistsOfLetters();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoMonthFieldConsistsOfLetters, pathForCredit, statusCode);
    }

    //Поле 'Месяц' состоит из спецсимволов
    @Test
    void MonthFieldWithSpecialCharacters() {
        var infoMonthFieldWithSpecialCharacters = DataGenerator.FormPayment.getMonthFieldWithSpecialCharacters();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoMonthFieldWithSpecialCharacters, pathForCredit, statusCode);
    }

    // Проверка поля "Год";

    // прошлый год
    @Test
    void PastYear() {
        var infoPastYear = DataGenerator.FormPayment.getPastYearCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoPastYear, pathForCredit, statusCode);
    }

    //год, за пределами срока обслуживания карты
    @Test
    void FutureYearOverCard() {
        var infoFutureYearOverCard = DataGenerator.FormPayment.getFutureYearOverCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoFutureYearOverCard, pathForCredit, statusCode);
    }

    //последний год обслуживания карты
    @Test
    void IfLastYearExpirationDate() {
        var infoIfLastYearExpirationDate = DataGenerator.FormPayment.getLastYearExpirationDate();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        apiHelper.sendPostStatusSuccess(infoIfLastYearExpirationDate, pathForCredit, statusCode);
    }

    //Поле 'Год' состоит из одного символа
    @Test
    void shouldGetErrorIfYearFieldConsistsOneCharacters() {
        var infoShouldGetErrorIfYearFieldConsistsOneCharacters = DataGenerator.FormPayment.getOneDigitYearCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoShouldGetErrorIfYearFieldConsistsOneCharacters, pathForCredit, statusCode);
    }

    //более 2 цифр
    @Test
    void InFieldUseMoreDigitsInYear() {
        var infoInFieldUseMoreDigitsInYear = DataGenerator.FormPayment.getMoreDigitsInYearCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoInFieldUseMoreDigitsInYear, pathForCredit, statusCode);
    }

    // ввод букв
    @Test
    void InYearFieldConsistsOfLetters() {
        var infoInYearFieldConsistsOfLetters = DataGenerator.FormPayment.getYearFieldConsistsOfLetters();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoInYearFieldConsistsOfLetters, pathForCredit, statusCode);
    }

    //Поле 'Год' состоит из спецсимволов;
    @Test
    void YearFieldWithSpecialCharacters() {
        var infoYearFieldWithSpecialCharacters = DataGenerator.FormPayment.getYearFieldWithSpecialCharacters();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoYearFieldWithSpecialCharacters, pathForCredit, statusCode);
    }

    //Поле 'Год' пустое;
    @Test
    void GetErrorIfYearFieldEmpty() {
        var infoGetErrorIfYearFieldEmpty = DataGenerator.FormPayment.getEmptyYearFieldCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoGetErrorIfYearFieldEmpty, pathForCredit, statusCode);
    }

    // проверка на текущие месяц и год
    @Test
    void CardWithCurrentPeriod() {
        var infoCardWithCurrentPeriod = DataGenerator.FormPayment.getCardWithCurrentPeriod();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        apiHelper.sendPostStatusSuccess(infoCardWithCurrentPeriod, pathForCredit, statusCode);
    }

    // Проверка поля "Владелец";

    // поле на кириллице
    @Test
    void GetErrorIfHolderFieldInCyrillic() {
        var infoGetErrorIfHolderFieldInCyrillic = DataGenerator.FormPayment.getCyrillicHolderCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoGetErrorIfHolderFieldInCyrillic, pathForCredit, statusCode);
    }

    // ввод спецсимволов
    @Test
    void GetErrorIfHolderFieldWithSpecialCharacters() {
        var infoGetErrorIfHolderFieldWithSpecialCharacters = DataGenerator.FormPayment.getSymbolHolderCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoGetErrorIfHolderFieldWithSpecialCharacters, pathForCredit, statusCode);
    }

    // ввод букв и цифр
    @Test
    void UseCardWithSymbolHolder() {
        var infoUseCardWithSymbolHolder = DataGenerator.FormPayment.getOwnerFieldWithLettersAndNumbers();
        var pathForPay = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoUseCardWithSymbolHolder, pathForPay, statusCode);
    }

    // пустое поле
    @Test
    void FieldEmptyHolder() {
        var infoFieldEmptyHolder = DataGenerator.FormPayment.getEmptyHolderFieldCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoFieldEmptyHolder, pathForCredit, statusCode);
    }

    // ввод одной буквы
    @Test
    void HolderFieldConsistsOneCharacters() {
        var infoHolderFieldConsistsOneCharacters = DataGenerator.FormPayment.getOwnerFieldConsistsOneCharacters();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoHolderFieldConsistsOneCharacters, pathForCredit, statusCode);
    }

    // ввод двойной фамилии
    @Test
    void HolderFieldWithDoubleSurname() {
        var infoHolderFieldWithDoubleSurname = DataGenerator.FormPayment.getOwnerFieldWithDoubleSurname();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        apiHelper.sendPostStatusSuccess(infoHolderFieldWithDoubleSurname, pathForCredit, statusCode);
    }

    // проверка на максимальный размер
    @Test
    void HolderFieldWithMaxLength() {
        var infoHolderFieldWithMaxLength = DataGenerator.FormPayment.getOwnerFieldWithMaxLength();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoHolderFieldWithMaxLength, pathForCredit, statusCode);
    }

    // Проверка поля "CVC"

    // ввод одной цифры
    @Test
    void CVCFieldConsistsOneCharacters() {
        var infoCVCFieldConsistsOneCharacters = DataGenerator.FormPayment.getOneDigitCvcCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCVCFieldConsistsOneCharacters, pathForCredit, statusCode);
    }

    // ввод 3 цифр
    @Test
    void UseMoreDigitsInCvc() {
        var infoUseMoreDigitsInCvc = DataGenerator.FormPayment.getMoreDigitsInCvcCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoUseMoreDigitsInCvc, pathForCredit, statusCode);
    }

    // ввод спецсимволов
    @Test
    void CVCFieldWithSpecialCharacters() {
        var infoCVCFieldWithSpecialCharacters = DataGenerator.FormPayment.getCVCFieldWithSpecialCharacters();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCVCFieldWithSpecialCharacters, pathForCredit, statusCode);
    }

    // ввод букв
    @Test
    void CVCFieldConsistsOfLetters() {
        var infoCVCFieldConsistsOfLetters = DataGenerator.FormPayment.getCVCFieldConsistsOfLetters();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCVCFieldConsistsOfLetters, pathForCredit, statusCode);
    }

    // пустое поле
    @Test
    void CVCFieldEmpty() {
        var infoCVCFieldEmpty = DataGenerator.FormPayment.getEmptyCvcFieldCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCVCFieldEmpty, pathForCredit, statusCode);
    }

    // пустая форма оплаты
    @Test
    void shouldShowErrorsForEmptyFields() {
        var infoShouldShowErrorsForEmptyFields = DataGenerator.FormPayment.getEmptyFormCard();
        var pathForCredit = DataGenerator.getCreditPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoShouldShowErrorsForEmptyFields, pathForCredit, statusCode);
    }
}

