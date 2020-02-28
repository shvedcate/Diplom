package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.LocalDate;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class InvalidData {
    private static SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private static SelenideElement monthInputField = $("[placeholder='08']");
    private static SelenideElement yearInputField = $("[placeholder='22']");
    private static SelenideElement cvcInputField = $("[placeholder='999']");
    private static SelenideElement ownerField = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private static SelenideElement continueButton = $$("button").find(Condition.exactText("Продолжить"));
    private SelenideElement successNotification = $(withText("Операция одобрена Банком."));
    private static SelenideElement errorNotification = $(withText("Ошибка! Банк отказал в проведении операции."));

    private static SelenideElement cardErrorText = $("div:nth-child(1) > span > span > span.input__sub");
    private static SelenideElement monthErrorText = $("div:nth-child(2) > span > span:nth-child(1) > span > span > span.input__sub");
    private static SelenideElement yearErrorText = $("div:nth-child(2) > span > span:nth-child(2) > span > span > span.input__sub");
    private static SelenideElement cvcErrorText = $("div:nth-child(3) > span > span:nth-child(2) > span > span > span.input__sub");
    private static SelenideElement ownerErrorText = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__sub");

    public static void putInvalidCardData() {
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


    public static void putInvalidYearAndMonth(DataHelper.CardInfo info) {
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

    public static void putValidYearAndPastMonth(DataHelper.CardInfo info) {
        cardNumberField.setValue(DataHelper.declinedCardInfo().getCardNumber());
        monthInputField.setValue(InvalidData.getRandomMinusTwoMonth());
        yearInputField.setValue("20");
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        continueButton.click();

        monthErrorText.shouldHave(Condition.exactText("Неверно указан срок действия карты")).
                getCssValue("color: #ff5c5c;");
    }

    public static void putFutureYearAndValidData(DataHelper.CardInfo info) {
        cardNumberField.setValue(DataHelper.approvedCardInfo().getCardNumber());
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue("35");
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        continueButton.click();

        yearErrorText.shouldHave(Condition.exactText("Неверно указан срок действия карты")).
                getCssValue("color: #ff5c5c;");
    }

    public static void putRussianOwnerName(DataHelper.CardInfo info) {
        cardNumberField.setValue(DataHelper.approvedCardInfo().getCardNumber());
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(DataHelper.generateOwnerName());
        continueButton.click();

        ownerErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
    }

    public static void putEmptyData() {
        continueButton.click();

        cardErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        monthErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        yearErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        cvcErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
        ownerErrorText.shouldHave(Condition.exactText("Поле обязательно для заполнения")).getCssValue("color: #ff5c5c;");
    }

    public static void putTextInCardNumberField(DataHelper.CardInfo info) {
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        cardNumberField.setValue(info.getOwner());
        continueButton.click();

        cardErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
    }

    public static void putSymbolsInOwnerField(DataHelper.CardInfo info) {
        cardNumberField.setValue(DataHelper.approvedCardInfo().getCardNumber());
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue("ghj$$$uytr");
        continueButton.click();

        ownerErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
    }

    public static void putLiterasInFieldsForNumbers(DataHelper.CardInfo info) {
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

        public static void putUnrealCardNumber(DataHelper.CardInfo info) {
            cardNumberField.setValue("5555555555555555");
            monthInputField.setValue(info.getMonth());
            yearInputField.setValue(info.getYear());
            cvcInputField.setValue(info.getCvc());
            ownerField.setValue(info.getOwner());
            continueButton.click();
            errorNotification.waitUntil(Condition.visible, 35000);
        }

    public static void putNumbersInOwnerField(DataHelper.CardInfo info) {
        cardNumberField.setValue(DataHelper.declinedCardInfo().getCardNumber());
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getCvc());
        continueButton.click();
        ownerErrorText.shouldHave(Condition.exactText("Неверный формат")).getCssValue("color: #ff5c5c;");
    }





}
