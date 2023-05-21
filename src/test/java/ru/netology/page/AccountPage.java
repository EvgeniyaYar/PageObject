package ru.netology.page;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AccountPage {
    private final String BalanceStart = ", баланс:";
    public final String BalanceFinish = " р.";

    public AccountPage() {
        $("[data-test-id='action-deposit']").shouldBe(Condition.visible);
    }

    public TransferPage transferToFirstCard() {
        $$("[data-test-id='action-deposit']").first().click();
        return new TransferPage();
    }

    public TransferPage transferToSecondCard() {
        $$("[data-test-id='action-deposit']").get(1).click();
        return new TransferPage();
    }

    public int getFirstCardBalance() {
        String text = $$(".list__item").first().getText();
        return checkBalance(text);
    }
    public int getSecondCardBalance() {
        String text = $$(".list__item").get(1).text();
        return checkBalance(text);
    }

    public int checkBalance(String text) {
        int start = text.indexOf(BalanceStart);
        int finish = text.indexOf(BalanceFinish);
        String balance = text.substring(start + BalanceStart.length(), finish).trim();
        return Integer.parseInt(balance);
    }
}
