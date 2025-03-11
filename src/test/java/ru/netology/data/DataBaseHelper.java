package ru.netology.data;

import lombok.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class DataBaseHelper {
    private static final QueryRunner runner = new QueryRunner();
    private static final String url = System.getProperty("db.url", "jdbc:mysql://localhost:3366/app");
    private static final String user = System.getProperty("db.user", "vladi");
    private static final String password = System.getProperty("db.password", "password");

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Data
    public static class OrderEntity {
        private String credit_id;
        private String payment_id;
    }

    public static OrderEntity getOrder() throws SQLException {
        var sql = "SELECT credit_id, payment_id FROM order_entity ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            return runner.query(conn, sql, new BeanHandler<>(OrderEntity.class));
        }
    }

    @Data
    public static class PaymentEntity {
        private String status;
        private String transaction_id;
    }

    public static PaymentEntity getPayment() throws SQLException {
        var sql = "SELECT status, transaction_id FROM payment_entity ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            return runner.query(conn, sql, new BeanHandler<>(PaymentEntity.class));
        }
    }

    @Data
    public static class CreditRequestEntity {
        private String bank_id;
        private String status;
    }

    public static CreditRequestEntity getCredit() throws SQLException {
        var sql = "SELECT bank_id, status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            return runner.query(conn, sql, new BeanHandler<>(CreditRequestEntity.class));
        }
    }

    public static void clearAllData() throws SQLException {
        try (var conn = getConnection()) {
            runner.update(conn, "DELETE FROM credit_request_entity");
            runner.update(conn, "DELETE FROM payment_entity");
            runner.update(conn, "DELETE FROM order_entity");
        }
    }
}

