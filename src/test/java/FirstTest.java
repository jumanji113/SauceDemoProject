
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.apache.commons.lang3.StringUtils.split;

public class FirstTest extends BaseTest{

    @Test
    @DisplayName("Вход на страницу, и ввод логина и пароля")
    public void openWebsite() throws InterruptedException {
        String expectedLogin = "standard_user";
        String expectedPass = "secret_sauce";
        open("https://www.saucedemo.com/");
        //получаем весь текст, который есть
        String fullText = $x("//div[@id='login_credentials']").getText();
        //разделяем его на нужный логин
        String login = fullText.split("\n")[1].trim();
        //получаем весь текст, который есть
        String fullPass = $x("//div[@data-test='login-password']").getText();
        //разделяем его на нужный логин
        String pass = fullPass.split("\n")[1].trim();
        Assertions.assertEquals(expectedLogin, login);
        Assertions.assertEquals(expectedPass, pass);

        //получаем инпут логина и вводим полученое значение выше
        $x("//input[@id='user-name']").setValue(login);
        //получаем инпут пароля и вводим полученое значение выше
        $x("//input[@id='password']").setValue(pass);

        //после ввода значений, нажимаем кнопку входа
        $x("//input[@id='login-button']").click();

        String logo = $x("//div[@class='app_logo']").getText().trim();
        Assertions.assertEquals(logo, "Swag Labs");

        //добавление товара Sauce Labs Backpack в корзину
        $x("//button[@id='add-to-cart-sauce-labs-backpack']").click();
        //добавление товара Sauce Labs Bike Light в корзину
        $x("//button[@data-test='add-to-cart-sauce-labs-bike-light']").click();

        Thread.sleep(3000);
        String actualCartItem = $x("//span[@data-test='shopping-cart-badge']").getText();
        String i = "2";
        Assertions.assertEquals(i, actualCartItem);
        String expectedTextButton = "Remove";
        String removeButton = $x("//button[@data-test='remove-sauce-labs-backpack']").getText();
        Assertions.assertEquals(removeButton, expectedTextButton);


        $x("//a[@class='shopping_cart_link']").click();
        //Получаем список товаров из корзины
        ElementsCollection cartItems = $$x("//div[@class='cart_item']");
        //Проверяем, что кол-во элементов равно 2
        Assertions.assertTrue(cartItems.size() == 2);
        Thread.sleep(3000);
    }
}
