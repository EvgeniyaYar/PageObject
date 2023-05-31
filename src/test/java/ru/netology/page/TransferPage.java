package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement cardField = $("[data-test-id='from'] input");
    private SelenideElement button = $("[data-test-id='action-transfer']");

    public TransferPage() {
        $("[data-test-id='amount'] input").shouldBe(Condition.visible);
    }


    public AccountPage moneyTransferCorrect(String amount, DataGenerator.UserCards info) {
        amountField.setValue(amount);
        cardField.setValue(String.valueOf(info));
        button.click();
        return new AccountPage();
    }

    public void moneyTransferIncorrect(String amount, DataGenerator.UserCards info) {
        amountField.setValue(amount);
        cardField.setValue(String.valueOf(info));
        button.shouldBe(Condition.disabled);
    }
}
