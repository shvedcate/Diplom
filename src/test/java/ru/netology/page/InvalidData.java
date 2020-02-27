package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.LocalDate;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class InvalidData {
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthInputField = $("[placeholder='08']");
    private SelenideElement yearInputField = $("[placeholder='22']");
    private SelenideElement cvcInputField = $("[placeholder='999']");
    private SelenideElement ownerField = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement continueButton = $$("button").find(Condition.exactText("Продолжить"));
    private SelenideElement successNotification = $(withText("Операция одобрена Банком."));
    private SelenideElement errorNotification = $(withText("Ошибка! Банк отказал в проведении операции."));

    private SelenideElement cardErrorText = $("div:nth-child(1) > span > span > span.input__sub");
    private SelenideElement monthErrorText = $("div:nth-child(2) > span > span:nth-child(1) > span > span > span.input__sub");
    private SelenideElement yearErrorText = $("div:nth-child(2) > span > span:nth-child(2) > span > span > span.input__sub");
    private SelenideElement cvcErrorText = $("div:nth-child(3) > span > span:nth-child(2) > span > span > span.input__sub");
    private SelenideElement ownerErrorText = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__sub");

    public void putInvalidCardData() {
        cardNumberField.setValue("123");
        monthInputField.setValue("0");
        yearInputField.setValue("0");
        cvcInputField.setValue("6");
        ownerField.setValue(" ");
        continueButton.click();

        cardErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        monthErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        yearErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        cvcErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        ownerErrorText.shouldHave(Condition.exactText("Поле обязательно для заполнения")).getCssValue("color: #ff5c5c;");
    }


    public void putInvalidYearAndMonth(DataHelper.CardInfo info) {
        cardNumberField.setValue(DataHelper.approvedCardInfo().getCardNumber());
        monthInputField.setValue(info.getYear());
        yearInputField.setValue(info.getMonth());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        continueButton.click();

        yearErrorText.shouldHave(Condition.exactText("Истёк срок действия карты")).
                getCssValue("color: #ff5c5c;");
        monthErrorText.shouldHave(Condition.exactText("Неверно указан срок действия карты")).
                getCssValue("color: #ff5c5c;");
    }

    public static String getRandomMinusTwoMonth() {
        LocalDate today = LocalDate.now();
        String month = String.format("%tm", today.minusMonths(1));
        return (month);
    }

    public void putValidYearAndPastMonth(DataHelper.CardInfo info) {
        cardNumberField.setValue(DataHelper.declinedCardInfo().getCardNumber());
        monthInputField.setValue(InvalidData.getRandomMinusTwoMonth());
        yearInputField.setValue("20");
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        continueButton.click();

        monthErrorText.shouldHave(Condition.exactText("Неверно указан срок действия карты")).
                getCssValue("color: #ff5c5c;");
    }

    public void putFutureYearAndValidData(DataHelper.CardInfo info) {
        cardNumberField.setValue(DataHelper.approvedCardInfo().getCardNumber());
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue("35");
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        continueButton.click();

        yearErrorText.shouldHave(Condition.exactText("Неверно указан срок действия карты")).
                getCssValue("color: #ff5c5c;");
    }

    public void putRussianOwnerName(DataHelper.CardInfo info) {
        cardNumberField.setValue(DataHelper.approvedCardInfo().getCardNumber());
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(DataHelper.generateOwnerName());
        continueButton.click();

        ownerErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
    }

    public void putEmptyData() {
        continueButton.click();

        cardErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        monthErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        yearErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        cvcErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        ownerErrorText.shouldHave(Condition.exactText("Поле обязательно для заполнения")).getCssValue("color: #ff5c5c;");
    }

    public void putTextInCardNumberField(DataHelper.CardInfo info) {
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        cardNumberField.setValue(info.getOwner());
        continueButton.click();

        cardErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
    }

    public void putSymbolsInOwnerField(DataHelper.CardInfo info) {
        cardNumberField.setValue(DataHelper.approvedCardInfo().getCardNumber());
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue("fgh123lkj");
        continueButton.click();

        ownerErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
    }

    public void putLiterasInFieldsForNumbers(DataHelper.CardInfo info) {
        cardNumberField.setValue(info.getOwner());
        monthInputField.setValue(info.getOwner());
        yearInputField.setValue(info.getOwner());
        cvcInputField.setValue(info.getOwner());
        ownerField.setValue(info.getOwner());
        continueButton.click();

        monthErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        cardErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        yearErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        cvcErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        ownerErrorText.shouldHave(Condition.exactText(""));
        }




}
