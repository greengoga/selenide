package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;

class AppCardDeliveryTest {

    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSubmitSuccessfully() {
        String planningDate = generateDate(3, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("div .input[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
//        $("div .input[data-test-id='date'] .input__control").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("div .calendar-input .input__control").setValue(planningDate);
        $("div .input[data-test-id='name'] .input__control").setValue("вася пупкин");
        $("div .input[data-test-id='phone'] .input__control").setValue("+79219998877");
        $("div .checkbox[data-test-id='agreement']").click();
        $("div .button").click();
//        $("div .notification_visible[data-test-id='notification']");
        $(Selectors.withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
