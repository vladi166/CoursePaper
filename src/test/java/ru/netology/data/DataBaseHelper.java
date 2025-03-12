package ru.netology.data;

import java.sql.Connection;
import java.sql.DriverManager;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class DataBaseHelper {
    private static final String url = System.getProperty("db.url");
    private static final String user = "vladi";
    private static final String password = "password";

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    private static String getValue(String query) {
        var runner = new QueryRunner();
        var value = "";
        try (var conn = DataBaseHelper.getConnection()) {
            value = runner.query(conn, query, new ScalarHandler<>());
        }
        return value;
    }

    @SneakyThrows
    public static String getDebitCardTransactionStatus() {
        return getValue("SELECT status FROM payment_entity;");
    }

    @SneakyThrows
    public static String getCreditCardTransactionStatus() {
        return getValue("SELECT status FROM credit_request_entity;");
    }

    @SneakyThrows
    public static void cleanDB() {
        var connection = DataBaseHelper.getConnection();
        connection.createStatement().executeUpdate("TRUNCATE credit_request_entity;");
        connection.createStatement().executeUpdate("TRUNCATE payment_entity;");
        connection.createStatement().executeUpdate("TRUNCATE order_entity;");
    }
}

