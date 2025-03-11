package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private static final Faker faker = new Faker();
    private static final Faker fakerRU = new Faker(new Locale("ru"));

    private DataGenerator() {
    }

    @Value
    @Data
    public static class AuthInfo {
        String number;
        String month;
        String year;
        String holder;
        String cvc;
    }

    public static String getApprovedCardNumber() {
        return "1111 2222 3333 4444";
    }

    public static String getApprovedStatus() {
        return "APPROVED";
    }

    public static String getDeclinedCardNumber() {
        return "5555 6666 7777 8888";
    }

    public static String getDeclinedStatus() {
        return "DECLINED";
    }

    public static String getRandomCardNumber() {
        return faker.numerify("#### #### #### ####");
    }

    public static String getRandomMonth() {
        return String.format("%02d", faker.number().numberBetween(1, 13));
    }

    public static String getCurrentMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getDoubleZero() {
        return "00";
    }

    public static String get13Month() {
        return "13";
    }

    private static String generateValidYear() {
        LocalDate yearNow = LocalDate.now();
        return yearNow.format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String generateLastYear() {
        LocalDate yearLast = LocalDate.now().minusYears(1);
        return yearLast.format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String generateYearHasExpired() {
        LocalDate yearExpired = LocalDate.now().plusYears(6);
        return yearExpired.format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String generateYearBeforeExpirationDate() {
        return LocalDate.now().plusYears(faker.number().numberBetween(1, 4)).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getHolder() {
        return faker.name().name();
    }

    public static String getCyrillicHolder() {
        return fakerRU.name().firstName() + " " + fakerRU.name().lastName();
    }

    private static String getHolderWithDoubleSurname() {
        return faker.name().firstName() + " " + faker.name().lastName() + "-" + faker.name().lastName();
    }

    private static String generateSpecialCharacters() {
        Random rand = new Random();
        List<String> list = Arrays.asList("~", "`", "@", "!", "#", "$", "%", "^", "&", "*", "(", ")", "/", "+",
                "№", ";", ":", "?", "<", ">", "{", "}");
        int randomIndex = rand.nextInt(list.size());
        return list.get(randomIndex);
    }

    public static String getCVC() {
        return faker.numerify("###");
    }

    public static String getEmptyField() {
        return "";
    }

    public static String getOneDigit() {
        return faker.numerify("#");
    }

    public static class FormPayment {
        private FormPayment() {

        }
    }

    // Тестовые данные для проверки поля "Номер карты";
    public static AuthInfo getValidUser() { //статус карты Approved
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getDeclinedUser() { //статус карты Declained
        return new AuthInfo(
                getDeclinedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getAnyCardNumberUser() { //рандомный номер
        return new AuthInfo(
                getRandomCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getPartCardNumber() { //менее 16 цифр
        return new AuthInfo(
                getOneDigit(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getMoreDigitsInCardNumber() {// более 16 цифр
        return new AuthInfo(
                getRandomCardNumber() + getOneDigit(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getCardNumberFieldConsistsOfLetters() {// поле состоит из букв
        return new AuthInfo(
                "abcd",
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getCardNumberWithoutDigit() {// поле состоит из специальных символов
        return new AuthInfo(
                generateSpecialCharacters(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getEmptyCardNumber() {// пустое поле
        return new AuthInfo(
                getEmptyField(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    //  Тестовые данные для проверки поля "Месяц";
    public static AuthInfo getMonthDoubleZeroCard() {// нули в поле
        return new AuthInfo(
                getApprovedCardNumber(),
                getDoubleZero(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getMonthOverCard() {// ввод 13 в поле
        return new AuthInfo(
                getApprovedCardNumber(),
                get13Month(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getOneDigitMonthCard() {// одна цифра
        return new AuthInfo(
                getApprovedCardNumber(),
                getOneDigit(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getMoreDigitsInMonth() {// более 2 цифр
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth() + getOneDigit(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getEmptyMonthFieldCard() {// пустое поле
        return new AuthInfo(
                getApprovedCardNumber(),
                getEmptyField(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getMonthFieldConsistsOfLetters() {// буквы в поле
        return new AuthInfo(
                getApprovedCardNumber(),
                "abcd",
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getMonthFieldWithSpecialCharacters() {// спецсимволы
        return new AuthInfo(
                getApprovedCardNumber(),
                generateSpecialCharacters(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    //  Тестовые данные для проверки поля "Год";
    public static AuthInfo getPastYearCard() {// прошлый год
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateLastYear(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getFutureYearOverCard() {// год, за пределами срока обслуживания карты
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearHasExpired(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getYearBeforeExpirationDate() {// последний год обслуживания карты
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getOneDigitYearCard() {// одна цифра
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                getOneDigit(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getMoreDigitsInYearCard() {// более 2 цифр
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate() + getOneDigit(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getYearFieldConsistsOfLetters() {// ввод букв
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                "abcd",
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getYearFieldWithSpecialCharacters() {// ввод спецсимволов
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateSpecialCharacters(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getEmptyYearFieldCard() {// пустое поле
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                getEmptyField(),
                getHolder(),
                getCVC()
        );
    }

    public static AuthInfo getCardWithCurrentPeriod() {// проверка на текущие месяц и год
        return new AuthInfo(
                getApprovedCardNumber(),
                getCurrentMonth(),
                generateValidYear(),
                getHolder(),
                getCVC()
        );
    }

    //  Тестовые данные для проверки поля "Владелец";
    public static AuthInfo getCyrillicHolderCard() {// поле на кириллице
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getCyrillicHolder(),
                getCVC()
        );
    }

    public static AuthInfo getSymbolHolderCard() {// ввод спецсимволов
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                generateSpecialCharacters(),
                getCVC()
        );
    }

    public static AuthInfo getOwnerFieldWithLettersAndNumbers() {// ввод букв и цифр
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                "I4V5A6N",
                getCVC()
        );
    }

    public static AuthInfo getEmptyHolderFieldCard() {// пустое поле
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getEmptyField(),
                getCVC()
        );
    }

    public static AuthInfo getOwnerFieldConsistsOneCharacters() {// ввод одной буквы
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                "I",
                getCVC()
        );
    }

    public static AuthInfo getOwnerFieldWithDoubleSurname() {// ввод двойной фамилии
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolderWithDoubleSurname(),
                getCVC()
        );
    }

    public static AuthInfo getOwnerFieldWithMaxLength() {// проверка на максимальный размер
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                "IVVVVVVVAAAAAANNNNNNN IIIIIIVVVVAAANNNNOOVVVV",
                getCVC()
        );
    }

    //  Тестовые данные для проверки поля "CVC";
    public static AuthInfo getOneDigitCvcCard() {// ввод одной цифры
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getOneDigit()
        );
    }

    public static AuthInfo getMoreDigitsInCvcCard() {// ввод 3 цифр
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getCVC() + getOneDigit()
        );
    }

    public static AuthInfo getCVCFieldWithSpecialCharacters() {// ввод спецсимволов
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                generateSpecialCharacters()
        );
    }

    public static AuthInfo getCVCFieldConsistsOfLetters() {// ввод букв
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                "abc"
        );
    }

    public static AuthInfo getEmptyCvcFieldCard() {// пустое поле
        return new AuthInfo(
                getApprovedCardNumber(),
                getRandomMonth(),
                generateYearBeforeExpirationDate(),
                getHolder(),
                getEmptyField()
        );
    }

    public static AuthInfo getEmptyFormCard() {// пустая форма оплаты
        return new AuthInfo(
                getEmptyField(),
                getEmptyField(),
                getEmptyField(),
                getEmptyField(),
                getEmptyField()
        );
    }

    public static String getPayPath() {
        return "/api/v1/pay";
    }

    public static String getCreditPath() {
        return "/api/v1/credit";
    }

    public static int getStatusCodeSuccess() {return 200;}

    public static int getStatusCodeClientError() {return 400;}

}
