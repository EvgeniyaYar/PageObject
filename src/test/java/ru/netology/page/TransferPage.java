package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement cardField = $("[data-test-id='from'] input");
    private SelenideElement button = $("[data-test-id='action-transfer']");

    public TransferPage() {
        $("[data-test-id='amount'] input").shouldBe(Condition.visible);
    }


    public AccountPage moneyTransferCorrect(String amount, String card) {
        amountField.setValue(amount);
        cardField.setValue(card);
        button.click();
        return new AccountPage();
    }

    public AccountPage moneyTransferIncorrect(String amount, String card) {
        amountField.setValue(amount);
        cardField.setValue(card);
        button.shouldBe(Condition.disabled);
        return null;
    }
}
