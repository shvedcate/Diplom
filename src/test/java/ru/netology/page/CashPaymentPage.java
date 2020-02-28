package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CashPaymentPage {

    private SelenideElement heading = $$(".heading").find(Condition.exactText("Оплата по карте"));
    private static SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private static SelenideElement monthInputField = $("[placeholder='08']");
    private static SelenideElement yearInputField = $("[placeholder='22']");
    private static SelenideElement cvcInputField = $("[placeholder='999']");
    private static SelenideElement ownerField = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private static SelenideElement continueButton = $$("button").find(Condition.exactText("Продолжить"));
    private static SelenideElement successNotification = $(withText("Операция одобрена Банком."));
    private static SelenideElement errorNotification = $(withText("Ошибка! Банк отказал в проведении операции."));


    public CashPaymentPage() {
        heading.waitUntil(Condition.exist, 5000);
    }

    public static void getCardNumber(DataHelper.CardNumber info) {
        cardNumberField.setValue(info.getCardNumber());
    }

    public static void putValidCardData(DataHelper.CardInfo info) {
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        continueButton.click();
    }

    public static void validVerify() {
        successNotification.waitUntil(Condition.visible, 35000);
    }

    public static void errorVerify() {
        errorNotification.waitUntil(Condition.visible, 35000);
    }




}

