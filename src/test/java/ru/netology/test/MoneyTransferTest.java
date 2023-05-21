package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    private static int transferAmount;
    private static int previousBalanceFirst;
    private static int previousBalanceSecond;

    LoginPage loginPage = new LoginPage();
    DataGenerator.UserInfo userInfo = DataGenerator.getUserInfo();
    DataGenerator.AuthorizationCode code = DataGenerator.getAuthorizationCode();

    @BeforeEach
    public void setup() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldTransferMoneyToFirstCard() {
        loginPage
                .validLogin(userInfo)
                .validCode(code)
                .transferToFirstCard()
                .moneyTransferCorrect(String.valueOf(DataGenerator.getTransferAmount().getAmount()),
                        userInfo.getSecondCardNumber());
    }

    @Test
    public void shouldTransferMoneyToSecondCard() {
        previousBalanceFirst = loginPage
                .validLogin(userInfo)
                .validCode(code)
                .getFirstCardBalance();
        open("http://localhost:9999/");
        previousBalanceSecond = loginPage
                .validLogin(userInfo)
                .validCode(code)
                .getSecondCardBalance();
        open("http://localhost:9999/");
        transferAmount = DataGenerator.getTransferAmount().getAmount();
        loginPage
                .validLogin(userInfo)
                .validCode(code)
                .transferToSecondCard()
                .moneyTransferCorrect(String.valueOf(transferAmount), userInfo.getFirstCardNumber());
    }

    @Test
    public void shouldCheckFirstCardBalance() {
        int expected = previousBalanceFirst - transferAmount;
        int actual = loginPage
                .validLogin(userInfo)
                .validCode(code)
                .getFirstCardBalance();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldCheckSecondCardBalance() {
        int expected = previousBalanceSecond + transferAmount;
        int actual = loginPage
                .validLogin(userInfo)
                .validCode(code)
                .getSecondCardBalance();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotTransferMoneyIfItIsNotEnough() {
        int balanceActual = loginPage
                .validLogin(userInfo)
                .validCode(code)
                .getSecondCardBalance();
        int amount = balanceActual + 100;
        open("http://localhost:9999/");
        loginPage
                .validLogin(userInfo)
                .validCode(code)
                .transferToFirstCard()
                .moneyTransferIncorrect(String.valueOf(amount),
                        userInfo.getSecondCardNumber());
    }
}
