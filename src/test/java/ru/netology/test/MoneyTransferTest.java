package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.page.AccountPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    private static int transferAmount;
    private static int firstCardBalance;
    private static int secondCardBalance;

    DataGenerator.UserInfo userInfo = DataGenerator.getUserInfo();
    DataGenerator.AuthorizationCode code = DataGenerator.getAuthorizationCode();
    DataGenerator.UserCards firstCard = DataGenerator.getFirstCardNumber();
    DataGenerator.UserCards secondCard = DataGenerator.getSecondCardNumber();

    @BeforeEach
    public void setup() {
        open("http://localhost:9999/");
    }


    @Test
    public void shouldTransferMoneyToFirstCard() {
        LoginPage loginPage = new LoginPage();
        loginPage.validLogin(userInfo).validCode(code);
        AccountPage accountPage = new AccountPage();
        firstCardBalance = accountPage.getFirstCardBalance();
        secondCardBalance = accountPage.getSecondCardBalance();
        transferAmount = DataGenerator.getTransferAmount(secondCardBalance).getAmount();
        int expectedFirstCard = firstCardBalance + transferAmount;
        int expectedSecondCard = secondCardBalance - transferAmount;
        accountPage
                .transferToFirstCard()
                .moneyTransferCorrect(String.valueOf(transferAmount), secondCard);
        int actualFirstCard = accountPage.getFirstCardBalance();
        int actualSecondCard = accountPage.getSecondCardBalance();
        Assertions.assertEquals(expectedFirstCard, actualFirstCard);
        Assertions.assertEquals(expectedSecondCard, actualSecondCard);

    }

    @Test
    public void shouldTransferMoneyToSecondCard() {
        LoginPage loginPage = new LoginPage();
        loginPage.validLogin(userInfo).validCode(code);
        AccountPage accountPage = new AccountPage();
        firstCardBalance = accountPage.getFirstCardBalance();
        secondCardBalance = accountPage.getSecondCardBalance();
        transferAmount = DataGenerator.getTransferAmount(firstCardBalance).getAmount();
        int expectedFirstCard = firstCardBalance - transferAmount;
        int expectedSecondCard = secondCardBalance + transferAmount;
        accountPage
                .transferToSecondCard()
                .moneyTransferCorrect(String.valueOf(transferAmount), firstCard);
        int actualFirstCard = accountPage.getFirstCardBalance();
        int actualSecondCard = accountPage.getSecondCardBalance();
        Assertions.assertEquals(expectedFirstCard, actualFirstCard);
        Assertions.assertEquals(expectedSecondCard, actualSecondCard);
    }

    @Test
    public void shouldNotTransferMoneyIfItIsNotEnough() {
        LoginPage loginPage = new LoginPage();
        firstCardBalance = loginPage
                .validLogin(userInfo)
                .validCode(code)
                .getFirstCardBalance();
        transferAmount = firstCardBalance + 100;
        AccountPage accountPage = new AccountPage();
        accountPage
                .transferToSecondCard()
                .moneyTransferIncorrect(String.valueOf(transferAmount),
                        firstCard);
    }
}

