package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Data
@AllArgsConstructor
public class AppProp {

    private String databaseUrl;
    private String userName;
    private String password;
    private String creditGateUrl;
    private String paymentGateUrl;

    public static AppProp getAppPropMySql() {
        FileInputStream fileInputStream;
        Properties prop = new Properties();
        try {
            fileInputStream = new FileInputStream("artifacts/application.properties");
            prop.load(fileInputStream);
        } catch (IOException e) {
            System.out.println("Ошибка! Файл свойств отсутствует.");
        }
        return new AppProp(prop.getProperty("spring.datasource.url_1"),
                prop.getProperty("spring.datasource.username"),
                prop.getProperty("spring.datasource.password"),
                prop.getProperty("spring.credit-gate.url"),
                prop.getProperty("spring.payment-gate.url"));
    }
    public static AppProp getAppPropPostgresql() {
        FileInputStream fileInputStream;
        Properties prop = new Properties();
        try {
            fileInputStream = new FileInputStream("artifacts/application.properties");
            prop.load(fileInputStream);
        } catch (IOException e) {
            System.out.println("Ошибка! Файл свойств отсутствует.");
        }
        return new AppProp(prop.getProperty("spring.datasource.url_2"),
                prop.getProperty("spring.datasource.username"),
                prop.getProperty("spring.datasource.password"),
                prop.getProperty("spring.credit-gate.url"),
                prop.getProperty("spring.payment-gate.url"));
    }
}
