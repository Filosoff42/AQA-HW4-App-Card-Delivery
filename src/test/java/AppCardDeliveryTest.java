import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.*;

class AppCardDeliveryTest {

    @Test
    void shouldTestSubmitSuccess() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        LocalDate date = LocalDate.now().plusDays(10);
        String stringDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
//        $("[data-test-id=date] input").clear();
        $("[data-test-id=date] input").doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(stringDate);
        $("[data-test-id=name] input").setValue("Григорий Харитонский");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement] span").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldFailIfInvalidCity() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Урюпинск");
        LocalDate date = LocalDate.now().plusDays(10);
        String stringDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(stringDate);
        $("[data-test-id=name] input").setValue("Григорий Харитонский");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement] span").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city] .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldFailIfInvalidDate() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        LocalDate date = LocalDate.now().plusDays(1);
        String stringDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(stringDate);
        $("[data-test-id=name] input").setValue("Григорий Харитонский");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement] span").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldFailIfInvalidName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        LocalDate date = LocalDate.now().plusDays(10);
        String stringDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(stringDate);
        $("[data-test-id=name] input").setValue("Grigoriy Kharitonskiy");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("[data-test-id=agreement] span").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name] .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldFailIfInvalidPhone() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        LocalDate date = LocalDate.now().plusDays(10);
        String stringDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(stringDate);
        $("[data-test-id=name] input").setValue("Григорий Харитонский");
        $("[data-test-id=phone] input").setValue("123");
        $("[data-test-id=agreement] span").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone] .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldFailIfAgreementUnchecked() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        LocalDate date = LocalDate.now().plusDays(10);
        String stringDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id=date] input").setValue(stringDate);
        $("[data-test-id=name] input").setValue("Григорий Харитонский");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));
    }
}
