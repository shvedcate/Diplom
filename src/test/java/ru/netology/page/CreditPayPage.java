package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPayPage {
    private static SelenideElement headingCredit = $$(".heading").find(Condition.exactText("Кредит по данным карты"));
    private static SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private static SelenideElement monthInputField = $("[placeholder='08']");
    private static SelenideElement yearInputField = $("[placeholder='22']");
    private static SelenideElement cvcInputField = $("[placeholder='999']");
    private static SelenideElement ownerField = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private static SelenideElement continueButton = $$("button").find(Condition.exactText("Продолжить"));
    private static SelenideElement successNotification = $(withText("Операция одобрена Банком."));
    private static SelenideElement errorNotification = $(withText("Ошибка! Банк отказал в проведении операции."));

    private SelenideElement cardErrorText = $("div:nth-child(1) > span > span > span.input__sub");
    private SelenideElement monthErrorText = $("div:nth-child(2) > span > span:nth-child(1) > span > span > span.input__sub");
    private SelenideElement yearErrorText = $("div:nth-child(2) > span > span:nth-child(2) > span > span > span.input__sub");
    private SelenideElement cvcErrorText = $("div:nth-child(3) > span > span:nth-child(2) > span > span > span.input__sub");
    private SelenideElement ownerErrorText = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__sub");


    public CreditPayPage() {
        headingCredit.shouldBe(Condition.visible);
    }

    public void getCardNumber(DataHelper.CardNumber info) {
        cardNumberField.setValue(info.getCardNumber());
    }

    public void putCardData (String number, String month, String year, String owner, String code) {
        cardNumberField.setValue(number);
        monthInputField.setValue(month);
        yearInputField.setValue(year);
        ownerField.setValue(owner);
        cvcInputField.setValue(code);
        continueButton.click();
    }

    public void putValidCardData(DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getOwner(), info.getCvc());

    }
    public void checkInvalidYearAndMonth(DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getYear(), info.getMonth(),
                info.getOwner(), info.getCvc());
        yearErrorText.shouldHave(Condition.exactText("Истёк срок действия карты")).
                getCssValue("color: #ff5c5c;");
        monthErrorText.shouldHave(Condition.exactText("Неверно указан срок действия карты")).
                getCssValue("color: #ff5c5c;");
    }

    public void checkAllInvalidData() {
        putCardData("123", "0", "0", " ", "6");
        cardErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        monthErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        yearErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        cvcErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        ownerErrorText.shouldHave(Condition.exactText("Поле обязательно для заполнения")).getCssValue("color: #ff5c5c;");
    }
    public void checkPastMonth(String pastMonth, DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), pastMonth, info.getYear(),
                info.getOwner(), info.getCvc());
        monthErrorText.shouldHave(Condition.exactText("Неверно указан срок действия карты")).
                getCssValue("color: #ff5c5c;");
    }
    public void checkFutureYear(String futureYear, DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.declinedCardInfo().getCardNumber(), info.getMonth(),
                futureYear, info.getOwner(), info.getCvc());
        yearErrorText.shouldHave(Condition.exactText("Неверно указан срок действия карты")).
                getCssValue("color: #ff5c5c;");
    }
    public void checkRussianOwnerName(String ownerNameRus, DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                ownerNameRus, info.getCvc());
        ownerErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");

    }
    public void checkEmptyData() {
        putCardData(" ", " ", " ", " ", " ");
        cardErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        monthErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        yearErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        cvcErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        ownerErrorText.shouldHave(Condition.exactText("Поле обязательно для заполнения")).getCssValue("color: #ff5c5c;");
    }
    public void checkTextInCardNumberField(DataHelper.ValidCardInfo info) {
        putCardData(info.getOwner(), info.getMonth(), info.getYear(), info.getOwner(), info.getCvc());
        cardErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
    }
    public void checkSymbolsInOwnerField(String symbolOwnerName, DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                symbolOwnerName, info.getCvc());
        ownerErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
    }
    public void checkLiterasInNumberFields(DataHelper.ValidCardInfo info) {
        putCardData(info.getOwner(), info.getOwner(), info.getOwner(), info.getOwner(),
                info.getOwner());
        monthErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        cardErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        yearErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        cvcErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        ownerErrorText.shouldHave(Condition.exactText(""));
    }
    public void checkUnrealCardNumber(String unrealCardNum, DataHelper.ValidCardInfo info) {
        putCardData(unrealCardNum, info.getMonth(), info.getYear(), info.getOwner(), info.getCvc());
        errorNotification.waitUntil(Condition.visible, 35000);
    }
    public void checkNumbersInOwnerField(DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.declinedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                DataHelper.approvedCardInfo().getCardNumber(), info.getCvc());
        ownerErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
    }

    public void validVerify() {
        successNotification.waitUntil(Condition.visible, 35000);
    }

    public void errorVerify() {
        errorNotification.waitUntil(Condition.visible, 35000);
    }
}
