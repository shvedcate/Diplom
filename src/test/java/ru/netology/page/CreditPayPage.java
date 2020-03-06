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
    private SelenideElement crossButtonInErrorNotification = $$(".notification_theme_alfa-on-white > button").last();

    private SelenideElement cardErrorText = $("div:nth-child(1) > span");
    private SelenideElement monthErrorText = $("div:nth-child(2) > span > span:nth-child(1)" );
    private SelenideElement yearErrorText = $("div:nth-child(2) > span > span:nth-child(2) > span");
    private SelenideElement cvcErrorText = $("div:nth-child(3) > span > span:nth-child(2) > span");
    private SelenideElement ownerErrorText = $("div:nth-child(3) > span > span:nth-child(1) > span");

    public CreditPayPage() {
        headingCredit.shouldBe(Condition.visible);
    }



    public void putCardData (String number, String month, String year, String owner, String code) {
        cardNumberField.setValue(number);
        monthInputField.setValue(month);
        yearInputField.setValue(year);
        ownerField.setValue(owner);
        cvcInputField.setValue(code);
        continueButton.click();
    }

    public void putValidDataApprovedCard(DataHelper.CardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getOwner(), info.getCvc());
        successNotification.waitUntil(Condition.visible, 35000);
    }
    public void putValidDataDeclinedCard(DataHelper.CardInfo info) {
        putCardData(DataHelper.declinedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getOwner(), info.getCvc());
        errorNotification.waitUntil(Condition.visible, 35000);
    }

    public void checkValidDataDeclinedCard(DataHelper.CardInfo info) {
        putCardData(DataHelper.declinedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getOwner(), info.getCvc());
        successNotification.waitUntil(Condition.visible, 35000);
    }

    public void checkInvalidYearAndMonth(DataHelper.CardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getYear(), info.getMonth(),
                info.getOwner(), info.getCvc());
        yearErrorText.shouldHave(Condition.exactText("Год Истёк срок действия карты")).
                getCssValue("color: #ff5c5c;");
        monthErrorText.shouldHave(Condition.exactText("Месяц Неверно указан срок действия карты")).
                getCssValue("color: #ff5c5c;");
    }

    public void checkAllInvalidData() {
        putCardData("123", "0", "0", " ", "6");
        cardErrorText.shouldHave(Condition.exactText("Номер карты Неверный формат")).getCssValue("color: #ff5c5c;");
        monthErrorText.shouldHave(Condition.exactText("Месяц Неверный формат")).getCssValue("color: #ff5c5c;");
        yearErrorText.shouldHave(Condition.exactText("Год Неверный формат")).getCssValue("color: #ff5c5c;");
        cvcErrorText.shouldHave(Condition.exactText("CVC/CVV Неверный формат")).getCssValue("color: #ff5c5c;");
        ownerErrorText.shouldHave(Condition.exactText("Владелец Поле обязательно для заполнения")).getCssValue("color: #ff5c5c;");
    }
    public void checkPastMonth(DataHelper.CardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getPastMonth(),
                info.getTodayYear(), info.getOwner(), info.getCvc());
        monthErrorText.shouldHave(Condition.exactText("Месяц Неверно указан срок действия карты")).
                getCssValue("color: #ff5c5c;");
    }
    public void checkFutureYear(DataHelper.CardInfo info) {
        putCardData(DataHelper.declinedCardInfo().getCardNumber(), info.getMonth(),
                info.getFutureYear(), info.getOwner(), info.getCvc());
        yearErrorText.shouldHave(Condition.exactText("Год Неверно указан срок действия карты")).
                getCssValue("color: #ff5c5c;");
    }
    public void checkRussianOwnerName(DataHelper.CardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getOwnerNameRus(), info.getCvc());
        ownerErrorText.shouldHave(Condition.exactText("Владелец Неверный формат")).getCssValue("color: #ff5c5c;");
    }
    public void checkEmptyData() {
        putCardData(" ", " ", " ", " ", " ");
        cardErrorText.shouldHave(Condition.exactText("Номер карты Неверный формат")).getCssValue("color: #ff5c5c;");
        monthErrorText.shouldHave(Condition.exactText("Месяц Неверный формат")).getCssValue("color: #ff5c5c;");
        yearErrorText.shouldHave(Condition.exactText("Год Неверный формат")).getCssValue("color: #ff5c5c;");
        cvcErrorText.shouldHave(Condition.exactText("CVC/CVV Неверный формат")).getCssValue("color: #ff5c5c;");
        ownerErrorText.shouldHave(Condition.exactText("Владелец Поле обязательно для заполнения")).getCssValue("color: #ff5c5c;");
    }
    public void checkTextInCardNumberField(DataHelper.CardInfo info) {
        putCardData(info.getOwner(), info.getMonth(), info.getYear(), info.getOwner(), info.getCvc());
        cardErrorText.shouldHave(Condition.exactText("Номер карты Неверный формат")).getCssValue("color: #ff5c5c;");
    }
    public void checkSymbolsInOwnerField(DataHelper.CardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getSymbolOwnerName(), info.getCvc());
        ownerErrorText.shouldHave(Condition.exactText("Владелец Неверный формат")).getCssValue("color: #ff5c5c;");
    }
    public void checkLiterasInNumberFields(DataHelper.CardInfo info) {
        putCardData(info.getOwner(), info.getOwner(), info.getOwner(), info.getOwner(),
                info.getOwner());
        monthErrorText.shouldHave(Condition.exactText("Месяц Неверный формат")).getCssValue("color: #ff5c5c;");
        cardErrorText.shouldHave(Condition.exactText("Номер карты Неверный формат")).getCssValue("color: #ff5c5c;");
        yearErrorText.shouldHave(Condition.exactText("Год Неверный формат")).getCssValue("color: #ff5c5c;");
        cvcErrorText.shouldHave(Condition.exactText("CVC/CVV Неверный формат")).getCssValue("color: #ff5c5c;");
        ownerErrorText.shouldHave(Condition.exactText("Владелец" + ""));
    }
    public void checkUnrealCardNumber(DataHelper.CardInfo info) {
        putCardData(info.getUnrealCardNum(),
                info.getMonth(), info.getYear(), info.getOwner(), info.getCvc());
        errorNotification.waitUntil(Condition.visible, 35000);
        crossButtonInErrorNotification.click();
        successNotification.shouldNotBe(Condition.visible);
    }
    public void checkNumbersInOwnerField(DataHelper.CardInfo info) {
        putCardData(DataHelper.declinedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                DataHelper.approvedCardInfo().getCardNumber(), info.getCvc());
        ownerErrorText.shouldHave(Condition.exactText("Владелец Неверный формат")).getCssValue("color: #ff5c5c;");
    }
}
