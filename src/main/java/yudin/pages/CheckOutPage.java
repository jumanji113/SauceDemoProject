package yudin.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

public class CheckOutPage {
    private ElementsCollection itemsPriceCollection = Selenide.$$x("//div[@class='inventory_item_price']");
    private SelenideElement allTotalSumm = Selenide.$x("//div[@data-test='subtotal-label']");

    @Step("Получение суммы всех предметов в корзине и проверка с итоговой суммой")
    public CheckOutPage checkSummItems() {
        int totalSum = itemsPriceCollection.stream()
                .map(element -> element.getText()) // Получаем текст каждого элемента
                .mapToInt(text -> {
                    try {
                        return Integer.parseInt(text.replaceAll("[^0-9]", "")); // Убираем все символы кроме цифр и парсим в число
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Failed to parse price from text: " + text, e);
                    }
                })
                .sum();
        Integer actualTotalSumm = Integer.parseInt(allTotalSumm.getText().replaceAll("[^0-9]", ""));
        Assertions.assertEquals(totalSum, actualTotalSumm, "суммы не совпадают");
        return this;
    }

}
