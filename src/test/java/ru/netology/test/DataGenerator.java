package ru.netology.test;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataGenerator {
    private DataGenerator() {
    }
    @Value
    public static class UserInfo {
        private final String login;
        private final String password;
        private final String firstCardNumber;
        private final String secondCardNumber;
    }
    public static UserInfo getUserInfo() {
        return new UserInfo("vasya", "qwerty123","5559000000000001", "5559000000000002");
    }
    @Value
    public static class AuthorizationCode {
        private final String code;
    }
    public static AuthorizationCode getAuthorizationCode() {
        return new AuthorizationCode("12345");
    }
    @Value
    public static class TransferAmount {
        private final int amount;
    }
    public static TransferAmount getTransferAmount() {
        Faker faker = new Faker();
        return new TransferAmount(faker.number().numberBetween(1, 10000));
    }
}
