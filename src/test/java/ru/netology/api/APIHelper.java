package ru.netology.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.data.DataGenerator;

import static io.restassured.RestAssured.given;

public class APIHelper {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")  // Базовый URL
            .setPort(8080)                   // Порт для обращения к серверу
            .setAccept(ContentType.JSON)     // Ожидаем JSON в ответе
            .setContentType(ContentType.JSON)// Отправляем JSON
            .log(LogDetail.ALL)              // Логирование запросов для облегчения отладки тестов
            .build();

    public static String sendPostStatusSuccess(DataGenerator.AuthInfo holder, String path, int statusCode) {
        String status =
                given()
                        .spec(requestSpec)  // Подключаем ранее созданную спецификацию
                        .body(holder)       // Передаем тело запроса (holder)
                        .when()
                        .post(path)         // Выполняем POST-запрос по переданному пути
                        .then()
                        .statusCode(statusCode)  // Проверяем ожидаемый код ответа
                        .extract()
                        .path("status");   // Извлекаем из JSON-ответа поле "status"
        return status;
    }

    public void sendPostStatusError(DataGenerator.AuthInfo holder, String path, int statusCode) {
        given()
                .spec(requestSpec)
                .body(holder)
                .when()
                .post(path)
                .then()
                .statusCode(statusCode);  // Проверяем, что сервер вернул нужный код ошибки
    }
}
