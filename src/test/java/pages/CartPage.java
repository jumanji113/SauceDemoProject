package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selenide.*;

public class CartPage {

    private ElementsCollection itemsCollections= $$x("//div[@class='cart_item']");
    private SelenideElement buttonContinue = $x("//button[@id = 'checkout']");


    @Step("Проверка количества элементов в корзине")
    public CartPage checkCountItems(Integer expectedSize){
        Integer sizeItemCollections = itemsCollections.size();
        Assertions.assertEquals(expectedSize, sizeItemCollections);
        return this;
    }

    @Step("Переход на заполнение данных пользователя")
    public UserInfoForm goToUserInfoPage(){
        buttonContinue.click();
        return page(UserInfoForm.class);
    }


}
