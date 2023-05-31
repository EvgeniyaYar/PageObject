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
    private static int cardBalance;


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
        cardBalance = loginPage
                .validLogin(userInfo)
                .validCode(code)
                .getSecondCardBalance();
        transferAmount = DataGenerator.getTransferAmount(cardBalance).getAmount();
        int expected = cardBalance - transferAmount;
        AccountPage accountPage = new AccountPage();
        accountPage
                .transferToFirstCard()
                .moneyTransferCorrect(String.valueOf(transferAmount), secondCard);
        int actual = accountPage.getSecondCardBalance();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldTransferMoneyToSecondCard() {
        LoginPage loginPage = new LoginPage();
        cardBalance = loginPage
                .validLogin(userInfo)
                .validCode(code)
                .getFirstCardBalance();
        transferAmount = DataGenerator.getTransferAmount(cardBalance).getAmount();
        int expected = cardBalance - transferAmount;
        AccountPage accountPage = new AccountPage();
        accountPage
                .transferToSecondCard()
                .moneyTransferCorrect(String.valueOf(transferAmount), firstCard);
        int actual = accountPage.getFirstCardBalance();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotTransferMoneyIfItIsNotEnough() {
        LoginPage loginPage = new LoginPage();
        cardBalance = loginPage
                .validLogin(userInfo)
                .validCode(code)
                .getFirstCardBalance();
        transferAmount = cardBalance + 100;
        AccountPage accountPage = new AccountPage();
        accountPage
                .transferToSecondCard()
                .moneyTransferIncorrect(String.valueOf(transferAmount),
                        firstCard);
    }
}

