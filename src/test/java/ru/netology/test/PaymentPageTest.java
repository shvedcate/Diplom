package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CashPaymentPage;
import ru.netology.page.CreditPayPage;
import ru.netology.page.InvalidData;
import ru.netology.page.PaymentChoosePage;

public class PaymentPageTest {

    //HAPPY PATH
    @Test
    @DisplayName("if pay by card should get success notification with APPROVED card and valid card data")
    void shouldBuyTourWithCashValidDataApprovedCard() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.getCardNumber(approvedCardInfo);
        cashPaymentPage.putValidCardData(cardInfo);
        cashPaymentPage.validVerify();
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
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.getCardNumber(declinedCardInfo);
        cashPaymentPage.putValidCardData(cardInfo);
        cashPaymentPage.errorVerify();
    }

    @Test
    @DisplayName("if pay by credit should get success notification with APPROVED card and valid card data")
    void shouldBuyTourWithCreditValidDataApprovedCard() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        val creditPayPage = new CreditPayPage();
        creditPayPage.getCardNumber(approvedCardInfo);
        creditPayPage.putValidCardData(cardInfo);
        creditPayPage.validVerify();
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
        val creditPayPage = new CreditPayPage();
        creditPayPage.getCardNumber(declinedCardInfo);
        creditPayPage.putValidCardData(cardInfo);
        creditPayPage.errorVerify();
    }

    //SAD PATH
    @Test
    @DisplayName("should get red text with error notification if put invalid data in card fields when pay by debit APPROVEDcard")
    void shouldBeErrorTextWithInvalidCardDataByDebit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val invalidData = new InvalidData();
        invalidData.putInvalidCardData();
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid data in card fields when pay by credit card")
    void shouldBeErrorTextWithInvalidCardDataByCredit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val invalidData = new InvalidData();
        invalidData.putInvalidCardData();
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid year and month when pay by debit card")
    void shouldBeErrorTextWithInvalidYearAndMonthByDebit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putInvalidYearAndMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid year and month when pay by credit card")
    void shouldBeErrorTextWithInvalidYearAndMonthByCredit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putInvalidYearAndMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put past month when pay by debit card")
    void shouldHaveErrorTextIfPutPastMonthInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putValidYearAndPastMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put past month when pay by credit")
    void shouldHaveErrorTextIfPutPastMonthInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putValidYearAndPastMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put future year when pay by debit card")
    void shouldHaveErrorTextIfPutFutureYearInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putFutureYearAndValidData(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put future year when pay by credit")
    void shouldHaveErrorTextIfPutFutureYearhInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putFutureYearAndValidData(cardInfo);
    }

   //BUG
   //Форма не должна отправиться, так как имя владельца карты всегда указывается латиницей. Но она отправляется
    @Test
    @DisplayName("should get red text with error notification if put owner name in kirilliza when pay by debit card")
    void shouldHaveErrorTextIfPutOwnerNameByKirillizaInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putRussianOwnerName(cardInfo);
    }
    //BUG
    //Форма не должна отправиться, так как имя владельца карты всегда указывается латиницей. Но она отправляется
    @Test
    @DisplayName("should get red text with error notification if put owner name in kirilliza when pay by credit card")
    void shouldHaveErrorTextIfPutOwnerNameByKirillizaInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putRussianOwnerName(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if data fields is empty when pay by debit card")
    void shouldHaveErrorTextIfDataFieldsIsEmptyInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val invalidData = new InvalidData();
        invalidData.putEmptyData();
    }
    @Test
    @DisplayName("should get red text with error notification if data fields is empty when pay by credit card")
    void shouldHaveErrorTextIfDataFieldsIsEmptyInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val invalidData = new InvalidData();
        invalidData.putEmptyData();
    }

    @Test
    @DisplayName("should get red text with error notification if put text in card number field when pay by debit card")
    void shouldHaveErrorTextIfPutTextInCardNumberFieldInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putTextInCardNumberField(cardInfo);
    }
    @Test
    @DisplayName("should get red text with error notification if put text in card number field when pay by credit card")
    void shouldHaveErrorTextIfPutTextInCardNumberFieldInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putTextInCardNumberField(cardInfo);
    }

    //BUG
    //Внизу поля "Владелец" должна появиться надпись "Неверный формат", но заявка проходит
    @Test
    @DisplayName("should get red text with error notification if put symbols in owner field when pay by debit card")
    void shouldHaveErrorTextIfPutSymbolsInOwnerFieldInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putSymbolsInOwnerField(cardInfo);
    }

    //BUG
    //Внизу поля "Владелец" должна появиться надпись "Неверный формат", но заявка проходит
    @Test
    @DisplayName("should get red text with error notification if put symbols in owner field when pay by credit card")
    void shouldHaveErrorTextIfPutSymbolsInOwnerFieldInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putSymbolsInOwnerField(cardInfo);
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
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putLiterasInFieldsForNumbers(cardInfo);
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
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putLiterasInFieldsForNumbers(cardInfo);
    }
}
