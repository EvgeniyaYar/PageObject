package ru.netology.page;

import ru.netology.test.DataGenerator;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public VerificationCodePage validLogin(DataGenerator.UserInfo info) {
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login']").click();
        return new VerificationCodePage();
    }
}
