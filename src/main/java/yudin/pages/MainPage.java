package yudin.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import yudin.constans.ItemEnum;

import java.time.Duration;
import java.util.*;

public class MainPage {

    private SelenideElement logo = Selenide.$x("//div[@class='app_logo']");
    private SelenideElement actualCartItem = Selenide.$x("//span[@data-test='shopping-cart-badge']");
    private SelenideElement cartButton = Selenide.$x("//a[@class='shopping_cart_link']");

    private Map<ItemEnum, SelenideElement> itemLocators = new HashMap<>();

    public MainPage() {
        itemLocators.put(ItemEnum.BACKPACK, Selenide.$x("//img[@alt='Sauce Labs Backpack']/../../.."));
        itemLocators.put(ItemEnum.BIKE_LIGHT, Selenide.$x("//img[@alt='Sauce Labs Bike Light']/../../.."));
        itemLocators.put(ItemEnum.BOLT_TSHIRT, Selenide.$x("//img[@alt='Sauce Labs Bolt T-Shirt']/../../.."));
        itemLocators.put(ItemEnum.FLEECE_JACKET, Selenide.$x("//img[@alt='Sauce Labs Fleece Jacket']/../../.."));
        itemLocators.put(ItemEnum.ONESIE, Selenide.$x("//img[@alt='Sauce Labs Onesie']/../../.."));
        itemLocators.put(ItemEnum.T_SHIRT_RED, Selenide.$x("//img[@alt='Test.allTheThings() T-Shirt (Red)']/../../.."));
    }

    @Step
    public String randomItemAddToCart(){
        List<Map.Entry<ItemEnum, SelenideElement>> entries = new ArrayList<>(itemLocators.entrySet());
        Random random = new Random();//класс рандом инициализируем
        int randomIndex = random.nextInt(entries.size());//получаем рандомное число по размеру листа
        clickAddButtonToCart(entries.get(randomIndex).getKey());//кликаем на кнопку добавить
        return getPriceItems(entries.get(randomIndex).getKey()).substring(1);//получаем строчку, которую в mainpage преобразуем в double, можно исправить
    }

    @Step("Получение цены {itemEnum}")
    public String getPriceItems(ItemEnum itemEnum) {
        SelenideElement itemLocator = itemLocators.get(itemEnum);
        if (itemLocator == null) {
            throw new IllegalArgumentException("Элемент не найден");
        }

        SelenideElement priceElement = itemLocator.$x(".//div[@data-test='inventory-item-price']");
        System.out.println(priceElement.getText().trim());
        return priceElement.getText().trim();
    }

    @Step("Добавление товара в корзину {itemEnum}")
    public void clickAddButtonToCart(ItemEnum itemEnum) {
        SelenideElement itemLocator = itemLocators.get(itemEnum);
        if (itemLocator == null) {
            throw new IllegalArgumentException("Элемент не найден");
        }
        SelenideElement button = itemLocator.$x(".//button[text() = 'Add to cart']");
        button.click();
    }

    @Step("Проверка лого сайта")
    public MainPage checkLogo(String expectedLogo) {
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
    public MainPage checkCounterCart(ItemEnum itemEnum) {
        SelenideElement button = itemLocators.get(itemEnum);
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

    @Step
    public CartPage clickCartButton() {
        cartButton.click();
        return Selenide.page(CartPage.class);
    }

}
