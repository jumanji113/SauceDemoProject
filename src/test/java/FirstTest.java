import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yudin.constans.SortOption;
import yudin.pages.CartPage;
import yudin.pages.InventoryPage;
import yudin.pages.SignUpPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FirstTest extends BaseTest {

    //откорректировал названия final переменных
    private final static String LOGIN = "standard_user";
    private final static String PASS = "secret_sauce";
    private final static String FIRST_NAME = "Alexey";
    private final static String LAST_NAME = "Jumanji";
    private final static int ZIP_CODE = 214031;
    //добавил изначальный список элементов
    private final static List<String> listItemsPricesInit = List.of("29.99", "9.99", "15.99", "49.99", "7.99", "15.99");
    private final static List<String> listItemsPricesHighToLow = listItemsPricesInit.stream()
            .sorted((s1, s2) -> Double.compare(Double.parseDouble(s2), Double.parseDouble(s1)))
            .toList();

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

    @Test
    @DisplayName("Проверка работы сортировки по имени по алфавиту от последней буквы к первой")
    public void checkWorkSortName() {
        new SignUpPage()
                .setData(LOGIN, PASS)
                .clickButtonSignUp();

        InventoryPage inventoryPage = new InventoryPage();
        Map<String, String> initialInventory = inventoryPage.getAllInventory();
        System.out.println(initialInventory);
        inventoryPage.sortItems(SortOption.NAMEZTOA);
        Map<String, String> actualInventory = inventoryPage.getAllInventory();
        System.out.println(actualInventory);

        //Преобразуем мапу в список
        List<Map.Entry<String, String>> initialList = new ArrayList<>(initialInventory.entrySet());
        System.out.println(initialList);
        List<Map.Entry<String, String>> actualList = new ArrayList<>(actualInventory.entrySet());
        System.out.println(actualList);

        //Получение 1 элемента изначального списка и получение последнего элемента после сортировки
        Map.Entry<String, String> firstElementInitialList = initialList.get(0);
        System.out.println(firstElementInitialList);
        Map.Entry<String, String> lastElementActualList = actualList.get(actualList.size() - 1);
        System.out.println(lastElementActualList);

        Assertions.assertEquals(firstElementInitialList.getKey(), lastElementActualList.getKey(),
                "Сравнение ключей первого элемента изначального списка и последнего элемента актуального списка");
        Assertions.assertEquals(firstElementInitialList.getValue(), lastElementActualList.getValue(),
                "Сравнение значений первого элемента изначального списка и последнего элемента актуального списка");

    }

    @Test
    @DisplayName("Проверка сортировки по цене от высокой к низкой")
    public void checkWorkSortPrice() {
        new SignUpPage()
                .setData(LOGIN, PASS)
                .clickButtonSignUp();

        InventoryPage inventoryPage = new InventoryPage();
        List<String> initAllInventoryItemPrices = inventoryPage.getAllPriceInventory();
        assertTrue(listItemsPricesInit.equals(initAllInventoryItemPrices), "Не совпал инициализируйщий массив");

        inventoryPage.sortItems(SortOption.PRICEDESC);
        List<String> actualInventoryItemPrices = inventoryPage.getAllPriceInventory();
        System.out.println(actualInventoryItemPrices);
        assertTrue(listItemsPricesHighToLow.equals(actualInventoryItemPrices), "Не совпал массив по убыванию");

    }

}
