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
    @DisplayName("if pay by card should get success notification with approved card and valid card data")
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
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.getCardNumber(approvedCardInfo);
        invalidData.putInvalidYearAndMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid year and month when pay by credit card")
    void shouldBeErrorTextWithInvalidYearAndMonthByCredit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val invalidData = new InvalidData();
        val declinedCardInfo = DataHelper.declinedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.getCardNumber(declinedCardInfo);
        invalidData.putInvalidYearAndMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put past month when pay by debit card")
    void shouldHaveErrorTextIfPutPastMonthInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val invalidData = new InvalidData();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.getCardNumber(approvedCardInfo);
        invalidData.putValidYearAndPastMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put past month when pay by credit")
    void shouldHaveErrorTextIfPutPastMonthInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val invalidData = new InvalidData();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.getCardNumber(approvedCardInfo);
        invalidData.putValidYearAndPastMonth(cardInfo);
    }




}
