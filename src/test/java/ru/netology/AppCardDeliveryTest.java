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

    @Test
    void shouldSubmitSuccessfully() {
        String planningDate = generateDate(3, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("div .input[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        $("div .calendar-input .input__control").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("div .calendar-input .input__control").setValue(planningDate);
        $("div .input[data-test-id='name'] .input__control").setValue("вася пупкин");
        $("div .input[data-test-id='phone'] .input__control").setValue("+79219998877");
        $("div .checkbox[data-test-id='agreement']").click();
        $("div .button").click();
        $(Selectors.withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldFailIfCityEmpty() {
        String planningDate = generateDate(3, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("div .calendar-input .input__control").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("div .calendar-input .input__control").setValue(planningDate);
        $("div .input[data-test-id='name'] .input__control").setValue("вася пупкин");
        $("div .input[data-test-id='phone'] .input__control").setValue("+79219998877");
        $("div .checkbox[data-test-id='agreement']").click();
        $("div .button").click();
        $(Selectors.withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldFailIfDateEmpty() {
        open("http://localhost:9999");
        $("div .input[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        $("div .calendar-input .input__control").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("div .input[data-test-id='name'] .input__control").setValue("вася пупкин");
        $("div .input[data-test-id='phone'] .input__control").setValue("+79219998877");
        $("div .checkbox[data-test-id='agreement']").click();
        $("div .button").click();
        $(Selectors.withText("Неверно введена дата")).shouldBe(Condition.visible);
    }

    @Test
    void shouldFailIfNameEmpty() {
        String planningDate = generateDate(3, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("div .input[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        $("div .calendar-input .input__control").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("div .calendar-input .input__control").setValue(planningDate);
        $("div .input[data-test-id='phone'] .input__control").setValue("+79219998877");
        $("div .checkbox[data-test-id='agreement']").click();
        $("div .button").click();
        $(Selectors.withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldFailIfPhoneEmpty() {
        String planningDate = generateDate(3, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("div .input[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        $("div .calendar-input .input__control").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("div .calendar-input .input__control").setValue(planningDate);
        $("div .input[data-test-id='name'] .input__control").setValue("вася пупкин");
        $("div .checkbox[data-test-id='agreement']").click();
        $("div .button").click();
        $(Selectors.withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldFailIfCheckboxUnchecked() {
        String planningDate = generateDate(3, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("div .input[data-test-id='city'] .input__control").setValue("Санкт-Петербург");
        $("div .calendar-input .input__control").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("div .calendar-input .input__control").setValue(planningDate);
        $("div .input[data-test-id='name'] .input__control").setValue("вася пупкин");
        $("div .input[data-test-id='phone'] .input__control").setValue("+79219998877");
        $("div .button").click();
        $(".checkbox.input_invalid").shouldBe(visible);
    }

    @Test
    void shouldChooseCityByTwoLetters() {
        String planningDate = generateDate(3, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("div .input[data-test-id='city'] .input__control").setValue("Са");
//        $$("span.menu-item__control").findBy(Condition.text("Санкт-Петербург")).click();
        $(By.xpath("//span[text()='Санкт-Петербург']")).click();
        $("div .calendar-input .input__control").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("div .calendar-input .input__control").setValue(planningDate);
        $("div .input[data-test-id='name'] .input__control").setValue("вася пупкин");
        $("div .input[data-test-id='phone'] .input__control").setValue("+79219998877");
        $("div .checkbox[data-test-id='agreement']").click();
        $("div .button").click();
        $(Selectors.withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldPickWeekAheadWithPopupCalendar() {
        String planningDate = generateDate(7, "d");
        open("http://localhost:9999");
        $("div .input[data-test-id='city'] .input__control").setValue("Са");
        $$("span.menu-item__control").findBy(Condition.text("Санкт-Петербург")).click();
        $("div .icon-button").click();
        $("div.calendar").$(Selectors.byText(planningDate)).click();
        $("div .input[data-test-id='name'] .input__control").setValue("вася пупкин");
        $("div .input[data-test-id='phone'] .input__control").setValue("+79219998877");
        $("div .checkbox[data-test-id='agreement']").click();
        $("div .button").click();
        $(Selectors.withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
