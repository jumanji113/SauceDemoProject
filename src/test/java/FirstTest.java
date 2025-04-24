
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import constans.ItemConstants;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.SignUpPage;

import static com.codeborne.selenide.Selenide.*;
import static org.apache.commons.lang3.StringUtils.split;

public class FirstTest extends BaseTest implements ItemConstants {

    private final static String expectedLogin = "standard_user";
    private final static String expectedPass = "secret_sauce";


    @Test
    @DisplayName("Вход на страницу, и ввод логина и пароля")
    @SneakyThrows
    public void openWebsite() {
        SignUpPage signUpPage = new SignUpPage();
        signUpPage.assertLoginAndPass(expectedLogin, expectedPass)
                .setData(expectedLogin, expectedPass)
                .clickButtonSignUp();

//        String actualCartItem = $x("//span[@data-test='shopping-cart-badge']").getText();
//        String expectedCountItems = "2";
//        Assertions.assertEquals(expectedCountItems, actualCartItem, "счетчик добавления товаров в корзину не работает");
//        String expectedTextButton = "Remove";
//        String removeButton = $x("//button[@data-test='remove-sauce-labs-backpack']").getText();
//        Assertions.assertEquals(expectedTextButton, removeButton, "Тип кнопки не поменялся");
//
//
//        $x("//a[@class='shopping_cart_link']").click();
//        //Получаем список товаров из корзины
//        ElementsCollection cartItems = $$x("//div[@class='cart_item']");
//        //Проверяем, что кол-во элементов равно 2
//        Assertions.assertEquals(2, cartItems.size());
//        Thread.sleep(3000);
    }

    @Test
    @DisplayName("Проверка работы счетчика корзины")
    public void checkCounter(){
        SignUpPage signUpPage = new SignUpPage();
        signUpPage
                .setData(expectedLogin, expectedPass)
                .clickButtonSignUp()
                .checkInitialStateCart()
                .checkCounterCart(ItemConstants.BACKPACK)
                .checkCounterCart(ItemConstants.BIKE_LIGHT);
    }



    @Test
    @DisplayName("Проверка лого")
    public void checkLogo(){
        String expectedLogo = "Swag Labs";
        SignUpPage signUpPage = new SignUpPage();
        signUpPage
                .setData(expectedLogin, expectedPass)
                .clickButtonSignUp()
                .checkLogo(expectedLogo);
    }
}
