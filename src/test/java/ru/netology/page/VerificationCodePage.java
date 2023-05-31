package ru.netology.page;

import com.codeborne.selenide.Condition;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;

public class VerificationCodePage {
    public VerificationCodePage() {
        $("[data-test-id='code'] input").shouldBe(Condition.visible);
    }
    public AccountPage validCode(DataGenerator.AuthorizationCode info) {
        $("[data-test-id='code'] input").setValue(info.getCode());
        $("[data-test-id='action-verify']").click();
        return new AccountPage();
    }

}
