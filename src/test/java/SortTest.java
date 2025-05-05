import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yudin.constans.SortOption;
import yudin.pages.InventoryPage;
import yudin.pages.LoginPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortTest extends BaseTest {

    private static final List<String> DEFAULT_PRICES = List.of("29.99", "9.99", "15.99", "49.99", "7.99", "15.99");
    private static final List<String> HIGH_TO_LOW_PRICES = DEFAULT_PRICES.stream()
            .sorted((s1, s2) -> Double.compare(Double.parseDouble(s2), Double.parseDouble(s1)))
            .toList();

    private InventoryPage inventoryPage;

    @BeforeEach
    public void setUpSecondTest() {
        new LoginPage().openPageAndSetDataLoginAndAccept();
        inventoryPage = new InventoryPage();
    }

    @Test
    @DisplayName("Проверка работы сортировки по имени (от Z до A)")
    public void verifySortByNameZToA() {
        List<String> initialTitles = inventoryPage.getAllTitleInventory();
        inventoryPage.sortItems(SortOption.NAMEZTOA);
        List<String> sortedTitles = inventoryPage.getAllTitleInventory();

        assertEquals(initialTitles.get(0), sortedTitles.get(sortedTitles.size() - 1),
                "Первый элемент исходного списка не совпадает с последним элементом отсортированного списка");
    }

    @Test
    @DisplayName("Проверка работы сортировки по цене (от высокой к низкой)")
    public void verifySortByPriceHighToLow() {
        List<String> initialPrices = inventoryPage.getAllPriceInventory();
        assertEquals(DEFAULT_PRICES, initialPrices, "Исходный список цен не совпадает с ожидаемым");

        inventoryPage.sortItems(SortOption.PRICEDESC);
        List<String> sortedPrices = inventoryPage.getAllPriceInventory();

        assertEquals(HIGH_TO_LOW_PRICES, sortedPrices, "Список цен после сортировки не совпадает с ожидаемым");
    }
}