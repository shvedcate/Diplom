package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.page.CashPaymentPage;
import ru.netology.page.CreditPayPage;
import ru.netology.page.PaymentChoosePage;

public class PaymentPageTest {

    @Test
    void shouldBuyTourWithCashAndValidData() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.cssTest();

    }

    @Test
    void shouldNotBuyTourWithCashAndInvalidData() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.cssInvalidTest();

    }

    @Test
    void shouldBuyTourWithCreditAndValidData() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val creditPayPage = new CreditPayPage();
        creditPayPage.cssTestCredit();
    }
}
