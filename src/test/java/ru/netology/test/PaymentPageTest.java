package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.CashPaymentPage;
import ru.netology.page.CreditPayPage;
import ru.netology.page.PaymentChoosePage;
import ru.netology.sqlUtils.SQLutils;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static ru.netology.sqlUtils.SQLutils.*;

public class PaymentPageTest {
    static DataHelper.CardInfo cardInfo;

    @AfterEach
    @DisplayName("Чистит базу данных перед каждым тестом")
    void cleanBase() throws SQLException {
        SQLutils.cleanDB();
    }

   @BeforeAll
   static void setupAll() throws SQLException{
       SelenideLogger.addListener("allure", new AllureSelenide());
       SQLutils.getConnection();
       cardInfo = DataHelper.getCardInfo();
   }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    static CashPaymentPage getCashPaymentPage() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCashPaymentPage();
        return new CashPaymentPage();
    }

    static CreditPayPage getCreditPayPage() {
        val paymentChoosePage = new PaymentChoosePage();
        paymentChoosePage.openPaymentChoosePage();
        paymentChoosePage.openCreditPayPage();
        return new CreditPayPage();
    }

    //HAPPY PATH

    //APPROVED card
    @Test
    @DisplayName("DB OrderEntity should not be empty, " +
            "should get success notification with APPROVED card, valid card data when pay by debit")
    void shouldBuyTourWithValidDataApprovedCardInDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        val actual = DataHelper.approvedCardInfo().getStatus();
        cashPaymentPage.putValidDataApprovedCard(cardInfo);
        val expected = getDebitCardStatus();
        assertEquals(expected, actual);
        val paymentEntityId = getPaymentEntityId(actual);
        assertNotEquals("", paymentEntityId);
        val orderId = getOrderEntityId(paymentEntityId);
        assertNotEquals("", orderId);
    }

    @Test
    @DisplayName("DB OrderEntity should not be empty, " +
            "should get success notification with APPROVED card and valid card data when pay by credit")
    void shouldBuyTourWithCreditValidDataApprovedCard() throws SQLException {
        val creditPayPage = getCreditPayPage();
        val actual = DataHelper.approvedCardInfo().getStatus();
        creditPayPage.putValidDataApprovedCard(cardInfo);
        val expected = getCreditCardStatus();
        assertEquals(expected, actual);
        val creditRequestEntityId = getCreditRequestEntityId(actual);
        assertNotEquals("", creditRequestEntityId);
        val orderId = getOrderEntityId(creditRequestEntityId);
        assertNotEquals("", orderId);
    }


    //DECLINED card

    //BUG должно появиться сообщение "Банк отказал в проведении операции" и в базе данных не должно быть записей
    @Test
    @DisplayName("should get error notification with DECLINED card and valid card data when pay by debit, " +
            "DB OrderEntity should be empty")
    void shouldGetErrorIfBuyWithCashValidDataAndDeclinedCard() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        cashPaymentPage.putValidDataDeclinedCard(cardInfo);
        val paymentEntityId = getPaymentEntityId(DataHelper.declinedCardInfo().getStatus());
        assertNotEquals("", paymentEntityId);
        emptyOrderEntity();
    }

    //BUG
    @Test
    @DisplayName("should get error notification with DECLINED card and valid card data when pay by credit, " +
            "DB OrderEntity should be empty")
    void shouldGetErrorWithCreditValidDataDeclinedCard() throws SQLException {
        val creditPayPage = getCreditPayPage();
        creditPayPage.putValidDataDeclinedCard(cardInfo);
        val creditRequestEntityId = getCreditRequestEntityId(DataHelper.declinedCardInfo().getStatus());
        assertNotEquals("", creditRequestEntityId);
        emptyOrderEntity();
    }

    //BUG Статус карты в базе должен быть DECLINED, но в таблице OrderEntity не должны появляться данные
    @Test
    @DisplayName("DB OrderEntity should be empty, " +
            "DECLINED card status should be equals with status in data base with valid card data when pay by debit")
    void declinedCardStatusShouldBeEqualsWithDBInDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        val actual = DataHelper.declinedCardInfo().getStatus();
        cashPaymentPage.checkValidDataDeclinedCard(cardInfo);
        val expected = getDebitCardStatus();
        assertEquals(expected, actual);
        val paymentEntityId = getPaymentEntityId(actual);
        assertNotEquals("", paymentEntityId);
        emptyOrderEntity();
    }

    //BUG Статус карты в базе должен быть DECLINED, но в таблице OrderEntity не должны появляться данные
    @Test
    @DisplayName("DB OrderEntity should be empty, " +
            "DECLINED card status should be equals with status in data base with valid card data when pay by credit")
    void declinedCardStatusShouldBeEqualsWithDBInCredit() throws SQLException {
        val creditPayPage = getCreditPayPage();
        val actual = DataHelper.declinedCardInfo().getStatus();
        creditPayPage.checkValidDataDeclinedCard(cardInfo);
        val expected = getCreditCardStatus();
        assertEquals(expected, actual);
        val creditEntityId = getCreditRequestEntityId(actual);
        assertNotEquals("", creditEntityId);
        emptyOrderEntity();
    }

    //SAD PATH
    @Test
    @DisplayName("should get red text with error notification if put invalid data in card fields when pay by debit APPROVED card")
    void shouldBeErrorTextWithInvalidCardDataByDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        cashPaymentPage.checkAllInvalidData();
        emptyPaymentEntity();
        emptyOrderEntity();
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid data in card fields when pay by credit APPROVED card")
    void shouldBeErrorTextWithInvalidCardDataByCredit() throws SQLException {
        val creditPayPage = getCreditPayPage();
        creditPayPage.checkAllInvalidData();
        emptyCreditEntity();
        emptyOrderEntity();
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid year and month when pay by debit card")
    void shouldBeErrorTextWithInvalidYearAndMonthByDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        cashPaymentPage.checkInvalidYearAndMonth(cardInfo);
        emptyPaymentEntity();
        emptyOrderEntity();
    }

    @Test
    @DisplayName("should get red text with error notification if put invalid year and month when pay by credit card")
    void shouldBeErrorTextWithInvalidYearAndMonthByCredit() throws SQLException {
        val creditPayPage = getCreditPayPage();
        creditPayPage.checkInvalidYearAndMonth(cardInfo);
        emptyCreditEntity();
        emptyOrderEntity();
    }

    @Test
    @DisplayName("should get red text with error notification if put past month when pay by debit card")
    void shouldHaveErrorTextIfPutPastMonthInDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        cashPaymentPage.checkPastMonth(cardInfo);
        emptyPaymentEntity();
        emptyOrderEntity();
    }

    @Test
    @DisplayName("should get red text with error notification if put past month when pay by credit")
    void shouldHaveErrorTextIfPutPastMonthInCredit() throws SQLException {
        val creditPayPage = getCreditPayPage();
        creditPayPage.checkPastMonth(cardInfo);
        emptyCreditEntity();
        emptyOrderEntity();
    }


    @Test
    @DisplayName("should get red text with error notification if put future year when pay by debit card")
    void shouldHaveErrorTextIfPutFutureYearInDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        cashPaymentPage.checkFutureYear(cardInfo);
        emptyPaymentEntity();
        emptyOrderEntity();
    }


    @Test
    @DisplayName("should get red text with error notification if put future year when pay by credit")
    void shouldHaveErrorTextIfPutFutureYearhInCredit() throws SQLException {
        val creditPayPage = getCreditPayPage();
        creditPayPage.checkFutureYear(cardInfo);
        emptyCreditEntity();
        emptyOrderEntity();
    }

   //BUG
   //Форма не должна отправиться, так как имя владельца карты всегда указывается латиницей. Но она отправляется
    @Test
    @DisplayName("should get red text with error notification if put owner name in kirilliza when pay by debit card")
    void shouldHaveErrorTextIfPutOwnerNameByKirillizaInDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        cashPaymentPage.checkRussianOwnerName(cardInfo);
        emptyPaymentEntity();
        emptyOrderEntity();
    }

    //BUG
    //Форма не должна отправиться, так как имя владельца карты всегда указывается латиницей. Но она отправляется
    @Test
    @DisplayName("should get red text with error notification if put owner name in kirilliza when pay by credit card")
    void shouldHaveErrorTextIfPutOwnerNameByKirillizaInCredit() throws SQLException {
        val creditPayPage = getCreditPayPage();
        creditPayPage.checkRussianOwnerName(cardInfo);
        emptyCreditEntity();
        emptyOrderEntity();
    }

    @Test
    @DisplayName("should get red text with error notification if data fields is empty when pay by debit card")
    void shouldHaveErrorTextIfDataFieldsIsEmptyInDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        cashPaymentPage.checkEmptyData();
        emptyPaymentEntity();
        emptyOrderEntity();
    }


    @Test
    @DisplayName("should get red text with error notification if data fields is empty when pay by credit card")
    void shouldHaveErrorTextIfDataFieldsIsEmptyInCredit() throws SQLException {
        val creditPayPage = getCreditPayPage();
        creditPayPage.checkEmptyData();
        emptyCreditEntity();
        emptyOrderEntity();
    }

    @Test
    @DisplayName("should get red text with error notification if put text in card number field when pay by debit card")
    void shouldHaveErrorTextIfPutTextInCardNumberFieldInDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        cashPaymentPage.checkTextInCardNumberField(cardInfo);
        emptyPaymentEntity();
        emptyOrderEntity();
    }


    @Test
    @DisplayName("should get red text with error notification if put text in card number field when pay by credit card")
    void shouldHaveErrorTextIfPutTextInCardNumberFieldInCredit() throws SQLException {
        val creditPayPage = getCreditPayPage();
        creditPayPage.checkTextInCardNumberField(cardInfo);
        emptyCreditEntity();
        emptyOrderEntity();
    }

    //BUG
    //Внизу поля "Владелец" должна появиться надпись "Неверный формат", но заявка проходит
    @Test
    @DisplayName("should get red text with error notification if put symbols in owner field when pay by debit card")
    void shouldHaveErrorTextIfPutSymbolsInOwnerFieldInDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        cashPaymentPage.checkSymbolsInOwnerField(cardInfo);
        emptyPaymentEntity();
        emptyOrderEntity();
    }


    //BUG
    //Внизу поля "Владелец" должна появиться надпись "Неверный формат", но заявка проходит
    @Test
    @DisplayName("should get red text with error notification if put symbols in owner field when pay by credit card")
    void shouldHaveErrorTextIfPutSymbolsInOwnerFieldInCredit() throws SQLException {
        val creditPayPage = getCreditPayPage();
        creditPayPage.checkSymbolsInOwnerField(cardInfo);
        emptyCreditEntity();
        emptyOrderEntity();
    }

    //BUG
    // В поле для владельца появляется надпись "Поле обязательно для заполнения",
    // хотя оно заполнено валидными данными
    @Test
    @DisplayName("should get red text with error notification if put literas in fields for numbers when pay by debit card")
    void shouldHaveErrorTextIfPutLiterasInDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        cashPaymentPage.checkLiterasInNumberFields(cardInfo);
        emptyPaymentEntity();
        emptyOrderEntity();
    }


    //BUG
    // В поле для владельца появляется надпись "Поле обязательно для заполнения",
    // хотя оно заполнено валидными данными
    @Test
    @DisplayName("should get red text with error notification if put literas in fields for numbers when pay by credit card")
    void shouldHaveErrorTextIfPutLiterasInCredit() throws SQLException {
        val creditPayPage = getCreditPayPage();
        creditPayPage.checkLiterasInNumberFields(cardInfo);
        emptyCreditEntity();
        emptyOrderEntity();
    }

    //BUG после сообщения об ошибочной операции появляется сообщение "Успешно! Операция одобрена банком"
    @Test
    @DisplayName("should get error notification if put unreal card number when pay by debit card")
    void shouldHaveErrorNotificationIfPutUnrealCardNumberInDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        cashPaymentPage.checkUnrealCardNumber(cardInfo);
        emptyPaymentEntity();
        emptyOrderEntity();
    }

    //BUG после сообщения об ошибочной операции появляется сообщение "Успешно! Операция одобрена банком"
    @Test
    @DisplayName("should get error notification if put unreal card number when pay by credit card")
    void shouldHaveErrorNotificationIfPutUnrealCardNumberInCredit() throws SQLException {
        val creditPayPage = getCreditPayPage();
        creditPayPage.checkUnrealCardNumber(cardInfo);
        emptyCreditEntity();
        emptyOrderEntity();
    }

    //BUG
    //Должна возникнуть надпись об ошибке
    //Заявка успешно отправляется
    @Test
    @DisplayName("should get text with error if put numbers in owner field when pay by debit card")
    void shouldHaveErrorTextIfPutNumbersInOwnerFieldInDebit() throws SQLException {
        val cashPaymentPage = getCashPaymentPage();
        cashPaymentPage.checkNumbersInOwnerField(cardInfo);
        emptyPaymentEntity();
        emptyOrderEntity();
    }


    //BUG
    //Должна возникнуть надпись об ошибке
    //Заявка успешно отправляется
    @Test
    @DisplayName("should get text with error if put numbers in owner field when pay by credit card")
    void shouldHaveErrorTextIfPutNumbersInOwnerFieldInCredit() throws SQLException {
        val creditPayPage = getCreditPayPage();
        creditPayPage.checkNumbersInOwnerField(cardInfo);
        emptyCreditEntity();
        emptyOrderEntity();
    }
}
