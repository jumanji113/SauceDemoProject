import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yudin.pages.CartPage;
import yudin.pages.InventoryPage;
import yudin.pages.LoginPage;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeEach
    public void setUpFirstTest() {
        loginPage = new LoginPage();
        inventoryPage = new InventoryPage();
        cartPage = new CartPage();
        loginPage.openPageAndSetDataLoginAndAccept();
    }

    @Test
    @DisplayName("Проверка лого")
    public void checkLogo() {
        String expectedLogo = "Swag Labs";
        inventoryPage.checkLogo(expectedLogo);
    }

    @Test
    @DisplayName("Проверка добавления рандомного элемента в корзину")
    public void verifyRandomItemAddedToCart() {
        inventoryPage.randomItemAddToCart();
    }

    @Test
    @DisplayName("Проверка совпадения суммы товаров на главной странице и в корзине")
    public void verifyCartSumMatchesInventorySum() {
        Map<String, String> itemsPrice = inventoryPage.addItemsPrice(inventoryPage.generateUniqueRandomIndexes0To6());
        inventoryPage.clickCartButton();

        Map<String, String> itemsInCart = cartPage.checkItemsInCart();

        double totalInventoryPrice = calculateTotal(itemsPrice);
        double totalCartPrice = calculateTotal(itemsInCart);

        assertEquals(totalInventoryPrice, totalCartPrice, "Сумма цен товаров в корзине не совпадает с суммой на главной странице");
    }

    private double calculateTotal(Map<String, String> items) {
        return items.values().stream()
                .mapToDouble(Double::parseDouble)
                .sum();
    }
}