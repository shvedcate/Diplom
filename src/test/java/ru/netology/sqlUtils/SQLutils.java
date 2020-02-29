package ru.netology.sqlUtils;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLutils {

    public static void cleanDataBase() throws SQLException {
        val cleanCreditRequest = "DELETE FROM credit_request_entity;";
        val cleanPayment = "DELETE FROM payment_entity;";
        val cleanOrder = "DELETE FROM order_entity;";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass")) {
            val cleanCardsUser = runner.execute(conn, cleanCreditRequest, new BeanHandler<>(CreditRequestEntity.class));
            val cleanAuthCodesUser = runner.execute(conn, cleanPayment, new BeanHandler<>(PaymentEntity.class));
            val cleanUserUser = runner.execute(conn, cleanOrder, new BeanHandler<>(OrderEntity.class));
        }
    }



    }
