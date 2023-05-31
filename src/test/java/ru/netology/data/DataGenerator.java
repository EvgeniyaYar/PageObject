package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataGenerator {
    private DataGenerator() {
    }

    @Value
    public static class UserInfo {
        private final String login;
        private final String password;
    }

    public static UserInfo getUserInfo() {
        return new UserInfo("vasya", "qwerty123");
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

    public static TransferAmount getTransferAmount(int cardBalance) {
        Faker faker = new Faker();
        return new TransferAmount(faker.number().numberBetween(1, cardBalance));
    }

    @Value
    public static class UserCards {
        private final String cardNumber;
    }

    public static UserCards getFirstCardNumber() {
        return new UserCards("5559000000000001");
    }

    public static UserCards getSecondCardNumber() {
        return new UserCards("5559000000000002");
    }
}
