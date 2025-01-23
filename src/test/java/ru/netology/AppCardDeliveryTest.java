package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

class AppCardDeliveryTest {


    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    String planningDate = generateDate(3, "dd.MM.yyyy");

    @Test
    void shouldSubmitSuccessfully() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("вася пупкин");
        $("[data-test-id='phone'] input").setValue("+79219998877");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void shouldFailIfCityEmpty() {
        open("http://localhost:9999");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("вася пупкин");
        $("[data-test-id='phone'] input").setValue("+79219998877");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldFailIfDateEmpty() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("вася пупкин");
        $("[data-test-id='phone'] input").setValue("+79219998877");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='date'] .input_invalid").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldFailIfNameEmpty() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='phone'] input").setValue("+79219998877");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldFailIfPhoneEmpty() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("вася пупкин");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldFailIfCheckboxUnchecked() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("вася пупкин");
        $("[data-test-id='phone'] input").setValue("+79219998877");
        $(".button").click();
        $(".checkbox.input_invalid").shouldBe(visible);
    }

//    @Test
//    void shouldChooseCityByTwoLetters() {
//        open("http://localhost:9999");
//        $("div .input[data-test-id='city'] .input__control").setValue("Са");
//        $$(".popup .menu-item").findBy(Condition.text("Санкт-Петербург")).click();
//        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
//        $("[data-test-id='date'] input").setValue(planningDate);
//        $("[data-test-id='name'] input").setValue("вася пупкин");
//        $("[data-test-id='phone'] input").setValue("+79219998877");
//        $("[data-test-id='agreement']").click();
//        $(".button").click();
//        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
//        $("[data-test-id='notification'] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + planningDate));
//    }

    @Test
    void shouldPickWeekAheadWithPopupCalendarAndCityByTwoLetters() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Са");
        $$(".popup .menu-item").findBy(Condition.text("Санкт-Петербург")).click();
        $(".icon-button").click();
        if(!generateDate(3,"MM").equals((generateDate(7, "MM")))) $(" .calendar__arrow_direction_right[data-step='1']").click();
        $$(".calendar__day").findBy(Condition.text(generateDate(7, "d"))).click();
        $("[data-test-id='name'] input").setValue("вася пупкин");
        $("[data-test-id='phone'] input").setValue("+79219998877");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + generateDate(7, "dd.MM.yyyy")));
    }
}
