package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CashPaymentPage;
import ru.netology.page.CreditPayPage;
import ru.netology.page.PaymentChoosePage;

public class PaymentPageTest {

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

    @Test
    @DisplayName("if pay by card should get error notification with declined card and valid card data")
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
    @DisplayName("if pay by credit should get success notification with approved card and valid card data")
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

    @Test
    @DisplayName("if pay by credit should get success notification with approved card and valid card data")
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


}
