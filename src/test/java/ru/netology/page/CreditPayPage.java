package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPayPage {
    private SelenideElement headingCredit = $$(".heading").find(Condition.exactText("Кредит по данным карты"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthInputField = $("[placeholder='08']");
    private SelenideElement yearInputField = $("[placeholder='22']");
    private SelenideElement cvcInputField = $("[placeholder='999']");
    private SelenideElement ownerField = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement continueButton = $$("button").find(Condition.exactText("Продолжить"));
    private SelenideElement successNotification = $(withText("Операция одобрена Банком."));
    private SelenideElement errorNotification = $(withText("Ошибка! Банк отказал в проведении операции."));


    public CreditPayPage() {
        headingCredit.shouldBe(Condition.visible);
    }

    public void cssTestCredit() {
        cardNumberField.setValue("4444444444444441");
        monthInputField.setValue("02");
        yearInputField.setValue("20");
        cvcInputField.setValue("123");
        ownerField.setValue("vasya");
        continueButton.click();
        successNotification.waitUntil(Condition.visible, 15000);

    }
}
