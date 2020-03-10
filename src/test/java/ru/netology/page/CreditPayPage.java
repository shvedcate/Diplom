package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.byText;
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

    private ElementsCollection wrongFormatError = $$(byText("Неверный формат"));
    private SelenideElement mustBeFieldError = $(byText("Поле обязательно для заполнения"));
    private SelenideElement cardPeriodError = $(byText("Истёк срок действия карты"));
    private SelenideElement invalidCardPeriodError = $(byText("Неверно указан срок действия карты"));

    public CreditPayPage() {
        headingCredit.shouldBe(Condition.visible);
    }

    public void putCardData(String number, String month, String year, String owner, String code) {
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
        cardPeriodError.shouldBe(Condition.visible);
        invalidCardPeriodError.shouldBe(Condition.visible);
    }

    public void checkAllInvalidData() {
        putCardData("123", "0", "0", " ", "6");
        wrongFormatError.shouldHaveSize(4);
        mustBeFieldError.shouldBe(Condition.visible);
    }

    public void checkPastMonth(DataHelper.CardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getPastMonth(),
                info.getTodayYear(), info.getOwner(), info.getCvc());
        invalidCardPeriodError.shouldBe(Condition.visible);
    }

    public void checkFutureYear(DataHelper.CardInfo info) {
        putCardData(DataHelper.declinedCardInfo().getCardNumber(), info.getMonth(),
                info.getFutureYear(), info.getOwner(), info.getCvc());
        invalidCardPeriodError.shouldBe(Condition.visible);
    }

    public void checkRussianOwnerName(DataHelper.CardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getOwnerNameRus(), info.getCvc());
        wrongFormatError.shouldHaveSize(1);
    }

    public void checkEmptyData() {
        putCardData(" ", " ", " ", " ", " ");
        wrongFormatError.shouldHaveSize(4);
        mustBeFieldError.shouldBe(Condition.visible);
    }

    public void checkTextInCardNumberField(DataHelper.CardInfo info) {
        putCardData(info.getOwner(), info.getMonth(), info.getYear(), info.getOwner(), info.getCvc());
        wrongFormatError.shouldHaveSize(1);
    }

    public void checkSymbolsInOwnerField(DataHelper.CardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getSymbolOwnerName(), info.getCvc());
        wrongFormatError.shouldHaveSize(1);
    }

    public void checkLiterasInNumberFields(DataHelper.CardInfo info) {
        putCardData(info.getOwner(), info.getOwner(), info.getOwner(), info.getOwner(),
                info.getOwner());
        wrongFormatError.shouldHaveSize(4);
        mustBeFieldError.shouldNotBe(Condition.visible);
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
        wrongFormatError.shouldHaveSize(1);
    }
}
