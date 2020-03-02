package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.AppProp;
import ru.netology.data.DataHelper;
import ru.netology.page.CashPaymentPage;
import ru.netology.page.CreditPayPage;
import ru.netology.page.PaymentChoosePage;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static ru.netology.sqlUtils.SQLutils.getOrderEntityId;
import static ru.netology.sqlUtils.SQLutils.getPaymentEntityId;

public class PaymentPageTest {

    static AppProp props;


   /* @BeforeEach
    @DisplayName("Should clean Data Base after login")
    void cleanBase() throws SQLException {
        SQLutils.cleanDataBase();
    }*/

   @BeforeAll
   static void setupAll() {
       props = AppProp.getAppPropMySql();
       props = AppProp.getAppPropPostgresql();
   }

    //HAPPY PATH
    @Test
    @DisplayName("should get success notification with APPROVED card, valid card data when pay by debit")
    void shouldBuyTourWithValidDataApprovedCardInDebit() throws SQLException {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.putValidDataApprovedCard(cardInfo);
        val paymentEntityId = getPaymentEntityId(props.getDatabaseUrl(), props.getUserName(), props.getPassword());
        assertNotEquals("", paymentEntityId);
        val orderId = getOrderEntityId(props.getDatabaseUrl(), props.getUserName(), props.getPassword());
        assertNotEquals("", orderId);
    }

   /* //BUG
    @Test
    @DisplayName("if pay by card should get error notification with DECLINED card and valid card data")
    void shouldGetErrorIfBuyWithCashValidDataAndDeclinedCard() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.putValidDataDeclinedCard(cardInfo);
    }

    @Test
    @DisplayName("if pay by credit should get success notification with APPROVED card and valid card data")
    void shouldBuyTourWithCreditValidDataApprovedCard() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPayPage = new CreditPayPage();
        creditPayPage.putValidDataApprovedCard(cardInfo);
    }

    //BUG
    @Test
    @DisplayName("if pay by credit should get success notification with DECLINED card and valid card data")
    void shouldGetErrorWithCreditValidDataDeclinedCard() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPayPage = new CreditPayPage();
        creditPayPage.putValidDataDeclinedCard(cardInfo);
    }

    //SAD PATH
    @Test
    @DisplayName("should get red text with error notification if put invalid data in card fields when pay by debit APPROVEDcard")
    void shouldBeErrorTextWithInvalidCardDataByDebit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.checkAllInvalidData();
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid data in card fields when pay by credit card")
    void shouldBeErrorTextWithInvalidCardDataByCredit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val creditPayPage = new CreditPayPage();
        creditPayPage.checkAllInvalidData();
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid year and month when pay by debit card")
    void shouldBeErrorTextWithInvalidYearAndMonthByDebit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.checkInvalidYearAndMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid year and month when pay by credit card")
    void shouldBeErrorTextWithInvalidYearAndMonthByCredit() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPayPage = new CreditPayPage();
        creditPayPage.checkInvalidYearAndMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put past month when pay by debit card")
    void shouldHaveErrorTextIfPutPastMonthInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.checkPastMonth(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if put past month when pay by credit")
    void shouldHaveErrorTextIfPutPastMonthInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPayPage = new CreditPayPage();
        creditPayPage.checkPastMonth(cardInfo);
    }


    @Test
    @DisplayName("should get red text with error notification if put future year when pay by debit card")
    void shouldHaveErrorTextIfPutFutureYearInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.checkFutureYear(cardInfo);
    }


    @Test
    @DisplayName("should get red text with error notification if put future year when pay by credit")
    void shouldHaveErrorTextIfPutFutureYearhInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPayPage = new CreditPayPage();
        creditPayPage.checkFutureYear(cardInfo);
    }

   //BUG
   //Форма не должна отправиться, так как имя владельца карты всегда указывается латиницей. Но она отправляется
    @Test
    @DisplayName("should get red text with error notification if put owner name in kirilliza when pay by debit card")
    void shouldHaveErrorTextIfPutOwnerNameByKirillizaInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.checkRussianOwnerName(cardInfo);
    }

    //BUG
    //Форма не должна отправиться, так как имя владельца карты всегда указывается латиницей. Но она отправляется
    @Test
    @DisplayName("should get red text with error notification if put owner name in kirilliza when pay by credit card")
    void shouldHaveErrorTextIfPutOwnerNameByKirillizaInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPayPage = new CreditPayPage();
        creditPayPage.checkRussianOwnerName(cardInfo);
    }

    @Test
    @DisplayName("should get red text with error notification if data fields is empty when pay by debit card")
    void shouldHaveErrorTextIfDataFieldsIsEmptyInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.checkEmptyData();
    }
    @Test
    @DisplayName("should get red text with error notification if data fields is empty when pay by credit card")
    void shouldHaveErrorTextIfDataFieldsIsEmptyInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val creditPayPage = new CreditPayPage();
        creditPayPage.checkEmptyData();
    }

    @Test
    @DisplayName("should get red text with error notification if put text in card number field when pay by debit card")
    void shouldHaveErrorTextIfPutTextInCardNumberFieldInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.checkTextInCardNumberField(cardInfo);
    }
    @Test
    @DisplayName("should get red text with error notification if put text in card number field when pay by credit card")
    void shouldHaveErrorTextIfPutTextInCardNumberFieldInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPayPage = new CreditPayPage();
        creditPayPage.checkTextInCardNumberField(cardInfo);
    }

    //BUG
    //Внизу поля "Владелец" должна появиться надпись "Неверный формат", но заявка проходит
    @Test
    @DisplayName("should get red text with error notification if put symbols in owner field when pay by debit card")
    void shouldHaveErrorTextIfPutSymbolsInOwnerFieldInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.checkSymbolsInOwnerField(cardInfo);
    }


    //BUG
    //Внизу поля "Владелец" должна появиться надпись "Неверный формат", но заявка проходит
    @Test
    @DisplayName("should get red text with error notification if put symbols in owner field when pay by credit card")
    void shouldHaveErrorTextIfPutSymbolsInOwnerFieldInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPayPage = new CreditPayPage();
        creditPayPage.checkSymbolsInOwnerField(cardInfo);
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
        val cardInfo = DataHelper.getValidCardInfo();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.checkLiterasInNumberFields(cardInfo);
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
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPayPage = new CreditPayPage();
        creditPayPage.checkLiterasInNumberFields(cardInfo);
    }

    @Test
    @DisplayName("should get error notification if put unreal card number when pay by debit card")
    void shouldHaveErrorNotificationIfPutUnrealCardNumberInDebit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.checkUnrealCardNumber(cardInfo);
    }
    @Test
    @DisplayName("should get error notification if put unreal card number when pay by credit card")
    void shouldHaveErrorNotificationIfPutUnrealCardNumberInCredit(){
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPayPage = new CreditPayPage();
        creditPayPage.checkUnrealCardNumber(cardInfo);
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
        val cardInfo = DataHelper.getValidCardInfo();
        val cashPaymentPage = new CashPaymentPage();
        cashPaymentPage.checkNumbersInOwnerField(cardInfo);
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
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPayPage = new CreditPayPage();
        creditPayPage.checkNumbersInOwnerField(cardInfo);
    }
*/
}
