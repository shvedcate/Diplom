package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.CashPaymentPage;
import ru.netology.page.CreditPayPage;
import ru.netology.page.InvalidData;
import ru.netology.page.PaymentChoosePage;
import ru.netology.sqlUtils.SQLutils;

import java.sql.SQLException;

public class PaymentPageTest {


   /* @BeforeEach
    @DisplayName("Should clean Data Base after login")
    void cleanBase() throws SQLException {
        SQLutils.cleanDataBase();
    }*/

    //HAPPY PATH
    @Test
    @DisplayName("should get success notification with APPROVED card, valid card data when pay by debit")
    void shouldBuyTourWithValidDataApprovedCardInDebit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        CashPaymentPage.getCardNumber(approvedCardInfo);
        CashPaymentPage.putValidCardData(cardInfo);
        CashPaymentPage.validVerify();
    }

    //BUG
    @Test
    @DisplayName("if pay by card should get error notification with DECLINED card and valid card data")
    void shouldGetErrorIfBuyWithCashValidDataAndDeclinedCard() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val declinedCardInfo = DataHelper.declinedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        CashPaymentPage.getCardNumber(declinedCardInfo);
        CashPaymentPage.putValidCardData(cardInfo);
        CashPaymentPage.errorVerify();
    }

    @Test
    @DisplayName("if pay by credit should get success notification with APPROVED card and valid card data")
    void shouldBuyTourWithCreditValidDataApprovedCard() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        CreditPayPage.getCardNumber(approvedCardInfo);
        CreditPayPage.putValidCardData(cardInfo);
        CreditPayPage.validVerify();
    }

    //BUG
    @Test
    @DisplayName("if pay by credit should get success notification with DECLINED card and valid card data")
    void shouldGetErrorWithCreditValidDataDeclinedCard() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val declinedCardInfo = DataHelper.declinedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        CreditPayPage.getCardNumber(declinedCardInfo);
        CreditPayPage.putValidCardData(cardInfo);
        CreditPayPage.errorVerify();
    }

    //SAD PATH
    @Test
    @DisplayName("should get red text with error notification if put invalid data in card fields when pay by debit APPROVEDcard")
    void shouldBeErrorTextWithInvalidCardDataByDebit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        InvalidData.putInvalidCardData();
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid data in card fields when pay by credit card")
    void shouldBeErrorTextWithInvalidCardDataByCredit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        InvalidData.putInvalidCardData();
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid year and month when pay by debit card")
    void shouldBeErrorTextWithInvalidYearAndMonthByDebit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putInvalidYearAndMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid year and month when pay by credit card")
    void shouldBeErrorTextWithInvalidYearAndMonthByCredit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putInvalidYearAndMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put past month when pay by debit card")
    void shouldHaveErrorTextIfPutPastMonthInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putValidYearAndPastMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put past month when pay by credit")
    void shouldHaveErrorTextIfPutPastMonthInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putValidYearAndPastMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put future year when pay by debit card")
    void shouldHaveErrorTextIfPutFutureYearInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putFutureYearAndValidData(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put future year when pay by credit")
    void shouldHaveErrorTextIfPutFutureYearhInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putFutureYearAndValidData(cardInfo);
    }

   //BUG
   //Форма не должна отправиться, так как имя владельца карты всегда указывается латиницей. Но она отправляется
    @Test
    @DisplayName("should get red text with error notification if put owner name in kirilliza when pay by debit card")
    void shouldHaveErrorTextIfPutOwnerNameByKirillizaInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putRussianOwnerName(cardInfo);
    }
    //BUG
    //Форма не должна отправиться, так как имя владельца карты всегда указывается латиницей. Но она отправляется
    @Test
    @DisplayName("should get red text with error notification if put owner name in kirilliza when pay by credit card")
    void shouldHaveErrorTextIfPutOwnerNameByKirillizaInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putRussianOwnerName(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if data fields is empty when pay by debit card")
    void shouldHaveErrorTextIfDataFieldsIsEmptyInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        InvalidData.putEmptyData();
    }
    @Test
    @DisplayName("should get red text with error notification if data fields is empty when pay by credit card")
    void shouldHaveErrorTextIfDataFieldsIsEmptyInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        InvalidData.putEmptyData();
    }

    @Test
    @DisplayName("should get red text with error notification if put text in card number field when pay by debit card")
    void shouldHaveErrorTextIfPutTextInCardNumberFieldInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putTextInCardNumberField(cardInfo);
    }
    @Test
    @DisplayName("should get red text with error notification if put text in card number field when pay by credit card")
    void shouldHaveErrorTextIfPutTextInCardNumberFieldInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putTextInCardNumberField(cardInfo);
    }

    //BUG
    //Внизу поля "Владелец" должна появиться надпись "Неверный формат", но заявка проходит
    @Test
    @DisplayName("should get red text with error notification if put symbols in owner field when pay by debit card")
    void shouldHaveErrorTextIfPutSymbolsInOwnerFieldInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putSymbolsInOwnerField(cardInfo);
    }

    //BUG
    //Внизу поля "Владелец" должна появиться надпись "Неверный формат", но заявка проходит
    @Test
    @DisplayName("should get red text with error notification if put symbols in owner field when pay by credit card")
    void shouldHaveErrorTextIfPutSymbolsInOwnerFieldInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putSymbolsInOwnerField(cardInfo);
    }

    //BUG
    // В поле для владельца появляется надпись "Поле обязательно для заполнения",
    // хотя оно заполнено валидными данными
    @Test
    @DisplayName("should get red text with error notification if put literas in fields for numbers when pay by debit card")
    void shouldHaveErrorTextIfPutLiterasInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putLiterasInFieldsForNumbers(cardInfo);
    }
    //BUG
    // В поле для владельца появляется надпись "Поле обязательно для заполнения",
    // хотя оно заполнено валидными данными
    @Test
    @DisplayName("should get red text with error notification if put literas in fields for numbers when pay by credit card")
    void shouldHaveErrorTextIfPutLiterasInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putLiterasInFieldsForNumbers(cardInfo);
    }

    @Test
    @DisplayName("should get error notification if put unreal card number when pay by debit card")
    void shouldHaveErrorNotificationIfPutUnrealCardNumberInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putUnrealCardNumber(cardInfo);
    }
    @Test
    @DisplayName("should get error notification if put unreal card number when pay by credit card")
    void shouldHaveErrorNotificationIfPutUnrealCardNumberInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putUnrealCardNumber(cardInfo);
    }

    //BUG
    //Должна возникнуть надпись об ошибке
    //Заявка успешно отправляется
    @Test
    @DisplayName("should get text with error if put numbers in owner field when pay by debit card")
    void shouldHaveErrorTextIfPutNumbersInOwnerFieldInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putNumbersInOwnerField(cardInfo);
    }
    //BUG
    //Должна возникнуть надпись об ошибке
    //Заявка успешно отправляется
    @Test
    @DisplayName("should get text with error if put numbers in owner field when pay by credit card")
    void shouldHaveErrorTextIfPutNumbersInOwnerFieldInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getCardInfo();
        InvalidData.putNumbersInOwnerField(cardInfo);
    }

}
