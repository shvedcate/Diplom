package ru.netology.sqlUtils;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLutils {


    public static void cleanDB() throws SQLException {
        val cleanCreditRequest = "DELETE FROM credit_request_entity;";
        val cleanPayment = "DELETE FROM payment_entity;";
        val cleanOrder = "DELETE FROM order_entity;";
        val runner = new QueryRunner();


           /*val cleanCreditRequestEntity = runner.execute(cleanCreditRequest, new BeanHandler<>(CreditRequestEntity.class));
           val cleanPaymentEntity = runner.execute(cleanPayment, new BeanHandler<>(PaymentEntity.class));
           val cleanOrderEntity = runner.execute(cleanOrder, new BeanHandler<>(OrderEntity.class));*/
        int result = runner.execute(cleanCreditRequest);
        result = runner.execute(cleanPayment);
        result = runner.execute(cleanOrder);
    }

    public static String getPaymentEntityId(String status) throws SQLException {
        val paymentEntity = "SELECT * FROM payment_entity WHERE status='" + status + "';";
        val runner = new QueryRunner();
        val paymentBlock = runner.query(paymentEntity, new BeanHandler<>(PaymentEntity.class));
            return paymentBlock.getTransaction_id();
    }

    public static String getCreditRequestEntityId(String status) throws SQLException {
        val creditRequest = "SELECT * FROM credit_request_entity WHERE status='" + status + "';";
        val runner = new QueryRunner();
        val creditRequestBlock = runner.query(creditRequest, new BeanHandler<>(CreditRequestEntity.class));
            return creditRequestBlock.getBank_id();
        }


    public static String getOrderEntityId(String paymentEntityId) throws SQLException {
        val orderEntity = "SELECT * FROM order_entity WHERE payment_id='" + paymentEntityId + "';";
        val runner = new QueryRunner();
        val orderBlock = runner.query(orderEntity, new BeanHandler<>(OrderEntity.class));
            return orderBlock.getId();
        }


    public static void getConnection() throws SQLException{
        try {
            String url = "spring.datasource.url";
            String username = "app";
            String password = "pass";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                System.out.println("Connection to Store DB success!");
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }

    }
}
