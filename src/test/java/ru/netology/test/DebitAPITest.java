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

public class DebitAPITest {
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
        var validDebitCard = DataGenerator.FormPayment.getValidUser();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        var response = apiHelper.sendPostStatusSuccess(validDebitCard, pathForPay, statusCode);
        assertEquals(DataGenerator.getApprovedStatus(), response);
    }

    //Номер карты со статусом: "DECLINED";
    @Test
    void shouldDeclinePayment() {
        var invalidDebitCard = DataGenerator.FormPayment.getDeclinedUser();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        var response = apiHelper.sendPostStatusSuccess(invalidDebitCard, pathForPay, statusCode);
        assertEquals(DataGenerator.getDeclinedStatus(), response);
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
    void ClientErrorStatusSendCardWithPartNumberForPay() {
        var infoHolder = DataGenerator.FormPayment.getPartCardNumber();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoHolder, pathForPay, statusCode);
    }

    //Поле 'Номер карты' не должно принимать более 16 цифр
    @Test
    void ClientErrorStatusSendMoreDigitsInCardNumberForPay() {
        var infoMoreDigitsInCardNumber = DataGenerator.FormPayment.getMoreDigitsInCardNumber();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoMoreDigitsInCardNumber, pathForPay, statusCode);
    }

    //буквы и цифры в поле
    @Test
    void CardNumberWithWordInNumber() {
        var infoCardNumberWithWordInNumber = DataGenerator.FormPayment.getCardNumberWithWordInNumber();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCardNumberWithWordInNumber, pathForPay, statusCode);
    }

    // поле состоит из букв
    @Test
    void CardNumberFieldConsistsLetters() {
        var infoCardNumberFieldConsistsLetters = DataGenerator.FormPayment.getCardNumberFieldConsistsOfLetters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCardNumberFieldConsistsLetters, pathForPay, statusCode);
    }

    // поле состоит из специальных символов
    @Test
    void CardNumberFieldWithSpecialCharacters() {
        var infoCardNumberFieldWithSpecialCharacters = DataGenerator.FormPayment.getCardNumberWithoutDigit();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCardNumberFieldWithSpecialCharacters, pathForPay, statusCode);
    }

    // пустое поле
    @Test
    void CardNumberFieldIsEmpty() {
        var infoCardNumberFieldIsEmpty = DataGenerator.FormPayment.getEmptyCardNumber();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCardNumberFieldIsEmpty, pathForPay, statusCode);
    }

    // Проверка поля "Месяц";

    //нули в поле
    @Test
    void UseMonthDoubleZero() {
        var infoUseMonthDoubleZero = DataGenerator.FormPayment.getMonthDoubleZeroCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoUseMonthDoubleZero, pathForPay, statusCode);
    }

    //Поле 'Месяц' заполнено несуществующей датой;
    @Test
    void InvalidMonthField() {
        var infoInvalidMonthField = DataGenerator.FormPayment.getMonthOverCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoInvalidMonthField, pathForPay, statusCode);
    }

    //Поле 'Месяц' состоит из одного символа;
    @Test
    void MonthFieldConsistsOneCharacters() {
        var infoMonthFieldConsistsOneCharacters = DataGenerator.FormPayment.getOneDigitMonthCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoMonthFieldConsistsOneCharacters, pathForPay, statusCode);
    }

    //более 2 цифр
    @Test
    void UseMoreDigitsInMonth() {
        var infoUseMoreDigitsInMonth = DataGenerator.FormPayment.getMoreDigitsInMonth();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoUseMoreDigitsInMonth, pathForPay, statusCode);
    }

    //пустое поле
    @Test
    void IfMonthFieldEmpty() {
        var infoIfMonthFieldEmpty = DataGenerator.FormPayment.getEmptyMonthFieldCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoIfMonthFieldEmpty, pathForPay, statusCode);
    }

    //Поле 'Месяц' состоит из букв
    @Test
    void MonthFieldConsistsOfLetters() {
        var infoMonthFieldConsistsOfLetters = DataGenerator.FormPayment.getMonthFieldConsistsOfLetters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoMonthFieldConsistsOfLetters, pathForPay, statusCode);
    }

    //Поле 'Месяц' состоит из спецсимволов
    @Test
    void MonthFieldWithSpecialCharacters() {
        var infoMonthFieldWithSpecialCharacters = DataGenerator.FormPayment.getMonthFieldWithSpecialCharacters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoMonthFieldWithSpecialCharacters, pathForPay, statusCode);
    }

    // Проверка поля "Год";

    // прошлый год
    @Test
    void PastYear() {
        var infoPastYear = DataGenerator.FormPayment.getPastYearCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoPastYear, pathForPay, statusCode);
    }

    //год, за пределами срока обслуживания карты
    @Test
    void FutureYearOverCard() {
        var infoFutureYearOverCard = DataGenerator.FormPayment.getFutureYearOverCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoFutureYearOverCard, pathForPay, statusCode);
    }

    //последний год обслуживания карты
    @Test
    void IfLastYearExpirationDate() {
        var infoIfLastYearExpirationDate = DataGenerator.FormPayment.getLastYearExpirationDate();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        apiHelper.sendPostStatusSuccess(infoIfLastYearExpirationDate, pathForPay, statusCode);
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
    void InFieldUseMoreDigitsInYear() {
        var infoInFieldUseMoreDigitsInYear = DataGenerator.FormPayment.getMoreDigitsInYearCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoInFieldUseMoreDigitsInYear, pathForPay, statusCode);
    }

    // ввод букв
    @Test
    void InYearFieldConsistsOfLetters() {
        var infoInYearFieldConsistsOfLetters = DataGenerator.FormPayment.getYearFieldConsistsOfLetters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoInYearFieldConsistsOfLetters, pathForPay, statusCode);
    }

    //Поле 'Год' состоит из спецсимволов;
    @Test
    void YearFieldWithSpecialCharacters() {
        var infoYearFieldWithSpecialCharacters = DataGenerator.FormPayment.getYearFieldWithSpecialCharacters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoYearFieldWithSpecialCharacters, pathForPay, statusCode);
    }

    //Поле 'Год' пустое;
    @Test
    void GetErrorIfYearFieldEmpty() {
        var infoGetErrorIfYearFieldEmpty = DataGenerator.FormPayment.getEmptyYearFieldCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoGetErrorIfYearFieldEmpty, pathForPay, statusCode);
    }

    // проверка на текущие месяц и год
    @Test
    void CardWithCurrentPeriod() {
        var infoCardWithCurrentPeriod = DataGenerator.FormPayment.getCardWithCurrentPeriod();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        apiHelper.sendPostStatusSuccess(infoCardWithCurrentPeriod, pathForPay, statusCode);
    }

    // Проверка поля "Владелец";

    // поле на кириллице
    @Test
    void GetErrorIfHolderFieldInCyrillic() {
        var infoGetErrorIfHolderFieldInCyrillic = DataGenerator.FormPayment.getCyrillicHolderCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoGetErrorIfHolderFieldInCyrillic, pathForPay, statusCode);
    }

    // ввод спецсимволов
    @Test
    void GetErrorIfHolderFieldWithSpecialCharacters() {
        var infoGetErrorIfHolderFieldWithSpecialCharacters = DataGenerator.FormPayment.getSymbolHolderCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoGetErrorIfHolderFieldWithSpecialCharacters, pathForPay, statusCode);
    }

    // ввод букв и цифр
    @Test
    void UseCardWithSymbolHolder() {
        var infoUseCardWithSymbolHolder = DataGenerator.FormPayment.getOwnerFieldWithLettersAndNumbers();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoUseCardWithSymbolHolder, pathForPay, statusCode);
    }

    // пустое поле
    @Test
    void FieldEmptyHolder() {
        var infoFieldEmptyHolder = DataGenerator.FormPayment.getEmptyHolderFieldCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoFieldEmptyHolder, pathForPay, statusCode);
    }

    // ввод одной буквы
    @Test
    void HolderFieldConsistsOneCharacters() {
        var infoHolderFieldConsistsOneCharacters = DataGenerator.FormPayment.getOwnerFieldConsistsOneCharacters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoHolderFieldConsistsOneCharacters, pathForPay, statusCode);
    }

    // ввод двойной фамилии
    @Test
    void HolderFieldWithDoubleSurname() {
        var infoHolderFieldWithDoubleSurname = DataGenerator.FormPayment.getOwnerFieldWithDoubleSurname();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeSuccess();
        apiHelper.sendPostStatusSuccess(infoHolderFieldWithDoubleSurname, pathForPay, statusCode);
    }

    // проверка на максимальный размер
    @Test
    void HolderFieldWithMaxLength() {
        var infoHolderFieldWithMaxLength = DataGenerator.FormPayment.getOwnerFieldWithMaxLength();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoHolderFieldWithMaxLength, pathForPay, statusCode);
    }

    // Проверка поля "CVC"

    // ввод одной цифры
    @Test
    void CVCFieldConsistsOneCharacters() {
        var infoCVCFieldConsistsOneCharacters = DataGenerator.FormPayment.getOneDigitCvcCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCVCFieldConsistsOneCharacters, pathForPay, statusCode);
    }

    // ввод 3 цифр
    @Test
    void UseMoreDigitsInCvc() {
        var infoUseMoreDigitsInCvc = DataGenerator.FormPayment.getMoreDigitsInCvcCard();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoUseMoreDigitsInCvc, pathForPay, statusCode);
    }

    // ввод спецсимволов
    @Test
    void CVCFieldWithSpecialCharacters() {
        var infoCVCFieldWithSpecialCharacters = DataGenerator.FormPayment.getCVCFieldWithSpecialCharacters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCVCFieldWithSpecialCharacters, pathForPay, statusCode);
    }

    // ввод букв
    @Test
    void CVCFieldConsistsOfLetters() {
        var infoCVCFieldConsistsOfLetters = DataGenerator.FormPayment.getCVCFieldConsistsOfLetters();
        var pathForPay = DataGenerator.getPayPath();
        var statusCode = DataGenerator.getStatusCodeClientError();
        apiHelper.sendPostStatusError(infoCVCFieldConsistsOfLetters, pathForPay, statusCode);
    }

    // пустое поле
    @Test
    void CVCFieldEmpty() {
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

