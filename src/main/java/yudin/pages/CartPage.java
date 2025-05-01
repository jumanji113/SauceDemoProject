package yudin.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

public class CartPage {

    private ElementsCollection itemsCollections = Selenide.$$x("//div[@class='cart_item']");
    private SelenideElement buttonContinue = Selenide.$x("//button[@id = 'checkout']");
    private ElementsCollection itemsCollectionInCart = Selenide.$$x("//div[@data-test = 'inventory-item']");

    @Step("Проверка количества элементов в корзине{expectedSize}")
    public CartPage checkCountItems(Integer expectedSize) {
        Integer sizeItemCollections = itemsCollections.size();
        Assertions.assertEquals(expectedSize, sizeItemCollections);
        return this;
    }

    @Step("Получение предметов в корзине")
    public Map<String, String> checkItemsInCart() {
        Map<String, String> itemsInCart = new HashMap<>();
        for (int i = 0; i < itemsCollectionInCart.size(); i++) {
            //получаем имя товара из корзины
            String nameItem = Selenide.$$x("//div[@data-test='inventory-item-name']").get(i).getText();
            //Получаем цену товара
            String priceItem = Selenide.$$x("//div[@data-test='inventory-item']//div[@class='inventory_item_price']").get(i).getText().substring(1);
            //Добавляем в мапу название и цену предмета
            itemsInCart.put(nameItem, priceItem);
        }
        return itemsInCart;
    }

//    @Step("Проверка суммы элементов в корзине")
//    public double checkSummAllItems() {
//        return itemsPriceCollection.stream()
//                .mapToDouble(element -> {
//                    String priceText = element.getText(); // Получаем текст элемента, например "$29.99"
//                    String numericValue = priceText.substring(1); // Убираем символ "$"
//                    return Double.parseDouble(numericValue); // Преобразуем строку в число
//                })
//                .sum();
//    }

    @Step("Переход на заполнение данных пользователя")
    public UserInfoForm goToUserInfoPage() {
        buttonContinue.click();
        return Selenide.page(UserInfoForm.class);
    }

}
