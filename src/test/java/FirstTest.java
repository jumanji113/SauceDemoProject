import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yudin.pages.CartPage;
import yudin.pages.InventoryPage;
import yudin.pages.SignUpPage;

import java.util.Map;

public class FirstTest extends BaseTest {

    //откорректировал названия final переменных
    private final static String LOGIN = "standard_user";
    private final static String PASS = "secret_sauce";
    private final static String FIRST_NAME = "Alexey";
    private final static String LAST_NAME = "Jumanji";
    private final static int ZIP_CODE = 214031;


    @Test
    @DisplayName("Вход на страницу, и ввод логина и пароля")
    @SneakyThrows
    public void openWebsite() {
        new SignUpPage().assertLoginAndPass(LOGIN, PASS)
                .setData(LOGIN, PASS)
                .clickButtonSignUp();
    }

    @Test
    @DisplayName("Проверка лого")
    public void checkLogo() {
        String expectedLogo = "Swag Labs";
        new SignUpPage()
                .setData(LOGIN, PASS)
                .clickButtonSignUp()
                .checkLogo(expectedLogo);
    }

    @Test
    @DisplayName("Проверка получения цены")
    public void checkPrice() {
        new SignUpPage()
                .setData(LOGIN, PASS)
                .clickButtonSignUp()
                .randomItemAddToCart();
    }

//    @Test
//    @DisplayName("Проверка добавления рандомного элемента в корзину и подсчета суммы в mainPage и сравнение с корзиной")
//    public void checkAddToCartAndCompare() {
//        new SignUpPage()
//                .setData(LOGIN, PASS)
//                .clickButtonSignUp();
//        MainPage mainPage = new MainPage();
//        String expectedPriceItemOneInMainPage = mainPage.randomItemAddToCart();
//        String expectedPriceItemTwoInMainPage = mainPage.randomItemAddToCart();
//        Double sumInMainPage = Double.parseDouble(expectedPriceItemOneInMainPage) + Double.parseDouble(expectedPriceItemTwoInMainPage);
//        mainPage.clickCartButton();
//        CartPage cartPage = new CartPage();
//        Double actualSummInCart = cartPage.checkSummAllItems();
//        Assertions.assertEquals(sumInMainPage, actualSummInCart);
//    }

    @Test
    @DisplayName("Проверка добавления рандомного элемента в корзину и подсчета суммы в mainPage и сравнение с корзиной")
    public void checkAddToCartAndCompare() {
        new SignUpPage()
                .setData(LOGIN, PASS)
                .clickButtonSignUp();

        InventoryPage inventoryPage = new InventoryPage();
        Map<String, String> itemsPrice = inventoryPage.addItemsPrice(inventoryPage.generateUniqueRandomIndexes0To6());

        inventoryPage.clickCartButton();

        CartPage cartPage = new CartPage();
        Map<String, String> itemsInCart = cartPage.checkItemsInCart();

        Double itemsPriceInInventoryPage = itemsPrice.values().stream()
                .mapToDouble(value -> Double.parseDouble(value)).sum();

        Double cartPageSummItems = itemsInCart.values().stream()
                .mapToDouble(value -> Double.parseDouble(value)).sum();

        Assertions.assertEquals(itemsPriceInInventoryPage, cartPageSummItems);
    }

}
