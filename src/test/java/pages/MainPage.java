package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import yudin.Main;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private SelenideElement logo = $x("//div[@class='app_logo']");
    private SelenideElement actualCartItem = $x("//span[@data-test='shopping-cart-badge']");

    private Map<String, SelenideElement> itemLocators = new HashMap<>();

    public MainPage() {
        itemLocators.put("backpack", $x("//button[@id='add-to-cart-sauce-labs-backpack']"));
        itemLocators.put("bike-light", $x("//button[@id='add-to-cart-sauce-labs-bike-light']"));
        itemLocators.put("bolt-tshirt", $x("//button[@id='add-to-cart-sauce-labs-bolt-t-shirt']"));
        itemLocators.put("fleece-jacket", $x("//button[@id='add-to-cart-sauce-labs-fleece-jacket']"));
        itemLocators.put("onesie", $x("//div[@id='add-to-cart-sauce-labs-onesie']"));
        itemLocators.put("t-shirt-red", $x("//div[@id='add-to-cart-test.allthethings()-t-shirt-(red)']"));
    }

    @Step("Проверка лого сайта")
    public MainPage checkLogo(String expectedLogo){
        String actualLogo = logo.getText().trim();
        Assertions.assertEquals(expectedLogo, actualLogo);
        return this;
    }

    @Step("Проверка обновления счетчика корзины")
    public MainPage checkCounterCart(String shortName){
        SelenideElement button = itemLocators.get(shortName);
        int counter = 0;
        if(button != null){
            button.click();
            counter++;
        } else {
            throw new IllegalArgumentException("Предмет не найден");
        }
        String actualCounter  = actualCartItem.getText();
        Assertions.assertEquals(Integer.toString(counter), actualCounter);
        return this;
    }

    @Step("Добавление элементов в корзину")
    public MainPage addItemToCart(String shortName){
        SelenideElement button = itemLocators.get(shortName);
        if(button != null){
            button.click();
        } else {
            throw new IllegalArgumentException("Предмет не найден");
        }
        return this;
    }
}
