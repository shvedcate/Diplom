package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() { }


    public static User generateForCard() {
        Faker faker = new Faker(new Locale("ru"));
        return new User(
                faker.name().firstName(),
                faker.name().lastName()
        );
    }

    @Value
    public static class CardInfo {
        private String month;
        private String year;
        private String cvc;
    }

    public static CardInfo getCardInfo() {
        String month = getRandomMonth();
        String year = getRandomYear();
        String cvc = getRandomCVC();

        return new CardInfo(month, year, cvc);
    }

    public static String getRandomMonth() {
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        Random random = new Random();
        int index = random.nextInt(months.length);
        return (months[index]);
    }

    public static String getRandomYear() {
        String[] years = {"20", "21", "22"};
        Random random = new Random();
        int index = random.nextInt(years.length);
        return (years[index]);
    }

    public static String getRandomCVC() {
        int a = 0;
        int b = 10;

        int num1 = a + (int) (Math.random() * b);
        int num2 = a + (int) (Math.random() * b);
        int num3 = a + (int) (Math.random() * b);
        String cvc = Integer.toString(num1) + num2 + num3;
        return cvc;
    }

    @Value
    public static class CardNumber {
        private String cardNumber;
    }
    public static CardNumber approvedCardInfo() {

        return new CardNumber("4444 4444 4444 4441");
    }
    public static CardNumber declinedCardInfo() {

        return new CardNumber("4444 4444 4444 4442");
    }
}





