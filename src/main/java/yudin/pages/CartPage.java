package yudin.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

public class CartPage {

    private ElementsCollection itemsCollections = Selenide.$$x("//div[@class='cart_item']");
    private SelenideElement buttonContinue = Selenide.$x("//button[@id = 'checkout']");

    @Step("Проверка количества элементов в корзине{expectedSize}")
    public CartPage checkCountItems(Integer expectedSize) {
        Integer sizeItemCollections = itemsCollections.size();
        Assertions.assertEquals(expectedSize, sizeItemCollections);
        return this;
    }

    @Step("Переход на заполнение данных пользователя")
    public UserInfoForm goToUserInfoPage() {
        buttonContinue.click();
        return Selenide.page(UserInfoForm.class);
    }

}
