package ru.netology.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginWithActiveUser() {
        var user = DataGenerator.createUser("active");

        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();

        $(byText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldNotLoginWithBlockedUser() {
        var user = DataGenerator.createUser("blocked");

        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']").shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldNotLoginWithInvalidCredentials() {
        $("[data-test-id='login'] input").setValue("nonexistent");
        $("[data-test-id='password'] input").setValue("wrongpass");
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification']").shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }
}