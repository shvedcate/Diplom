package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;
import ru.netology.data.User;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.netology.data.DataHelper.generateForCard;

public class CashPaymentPage {

    private SelenideElement heading = $$(".heading").find(Condition.exactText("Оплата по карте"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthInputField = $("[placeholder='08']");
    private SelenideElement yearInputField = $("[placeholder='22']");
    private SelenideElement cvcInputField = $("[placeholder='999']");
    private SelenideElement ownerField = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement continueButton = $$("button").find(Condition.exactText("Продолжить"));
    private SelenideElement successNotification = $(withText("Операция одобрена Банком."));
    private SelenideElement errorNotification = $(withText("Ошибка! Банк отказал в проведении операции."));


    public CashPaymentPage() {
        heading.waitUntil(Condition.exist, 5000);
    }

    public void getCardNumber(DataHelper.CardNumber info) {
        cardNumberField.setValue(info.getCardNumber());
    }

    public void putValidCardData(DataHelper.CardInfo info) {
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        User userName = generateForCard();
        ownerField.setValue(String.valueOf(userName));
        continueButton.click();
    }

    public void validVerify() {
        successNotification.waitUntil(Condition.visible, 35000);
    }

    public void errorVerify() {
        errorNotification.waitUntil(Condition.visible, 35000);
    }




}

