package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class MainPage {
    private SelenideElement logo = $x("//div[@class='app_logo']");
    private SelenideElement actualCartItem = $x("//span[@data-test='shopping-cart-badge']");
    private SelenideElement cartButton = $x("//a[@class='shopping_cart_link']");

    private Map<String, SelenideElement> itemLocators = new HashMap<>();

    public MainPage() {
        itemLocators.put("backpack", $x("//button[@id='add-to-cart-sauce-labs-backpack']"));
        itemLocators.put("bike-light", $x("//button[@id='add-to-cart-sauce-labs-bike-light']"));
        itemLocators.put("bolt-tshirt", $x("//button[@id='add-to-cart-sauce-labs-bolt-t-shirt']"));
        itemLocators.put("fleece-jacket", $x("//button[@id='add-to-cart-sauce-labs-fleece-jacket']"));
        itemLocators.put("onesie", $x("//button[@id='add-to-cart-sauce-labs-onesie']"));
        itemLocators.put("t-shirt-red", $x("//button[@id='add-to-cart-test.allthethings()-t-shirt-(red)']"));
    }

    @Step("Проверка лого сайта")
    public MainPage checkLogo(String expectedLogo){
        String actualLogo = logo.getText().trim();
        Assertions.assertEquals(expectedLogo, actualLogo);
        return this;
    }

    @Step("Проверка начального состояния корзины")
    public MainPage checkInitialStateCart() {
        if (!actualCartItem.exists()) {
            System.out.println("Элемент счетчик отсутствует на странице. Продолжение теста...");
            return this;
        }

        // Если элемент существует, проверяем его текст
        String initialCounterText = actualCartItem.getText();
        int initialCounter = initialCounterText.isEmpty() ? 0 : Integer.parseInt(initialCounterText);
        Assertions.assertEquals(0, initialCounter, "Корзина не пуста перед началом теста");
        return this;
    }

    @Step("Обновления счетчика корзины и добавление элементов")
    public MainPage checkCounterCart(String shortName) {
        SelenideElement button = itemLocators.get(shortName);
        if (button == null) {
            throw new IllegalArgumentException("Элемент не найден");
        }
        // Проверяем, существует ли счетчик корзины перед добавлением элемента
        boolean isCounterPresentInitially = actualCartItem.exists();
        int currentCounter = 0; // Изначально счетчик равен 0
        // Если счетчик уже существует, получаем его текущее значение
        if (isCounterPresentInitially) {
            String currentCounterText = actualCartItem.getText();
            currentCounter = currentCounterText.isEmpty() ? 0 : Integer.parseInt(currentCounterText);
        }
        // Добавляем элемент в корзину
        button.click();
        // Ожидаемое значение счетчика
        int expectedCounter = currentCounter + 1;
        // Проверяем, что счетчик появился (если его не было изначально)
        if (!isCounterPresentInitially) {
            actualCartItem.should(Condition.exist, Duration.ofSeconds(10)); // Ждем появления счетчика
        }
        // Получаем актуальное значение счетчика
        String actualCounterText = actualCartItem.getText();
        int actualCounter = actualCounterText.isEmpty() ? 0 : Integer.parseInt(actualCounterText);
        // Проверяем, что счетчик обновился
        Assertions.assertEquals(expectedCounter, actualCounter, "Счетчик корзины не обновился");
        return this;
    }

    @Step("Добавление элементов в корзину без проверки счетчика")
    public MainPage addItemToCart(String shortName){
        SelenideElement button = itemLocators.get(shortName);
        if(button == null){
            throw new IllegalArgumentException("Элемент не найден");
        }
        button.click();
        return this;
    }

    @Step
    public CartPage clickCartButton(){
        cartButton.click();
        return page(CartPage.class);
    }
}
