package yudin.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class UserInfoForm {

    private SelenideElement firstNameInput = Selenide.$x("//input[@id = 'first-name']");
    private SelenideElement lastNameInput = Selenide.$x("//input[@id = 'last-name']");
    private SelenideElement postalCodeInput = Selenide.$x("//input[@id = 'postal-code']");
    private SelenideElement buttonContinue = Selenide.$x("//input[@id = 'continue']");

    @Step("Заполнение данных пользователя")
    public CheckOutPage setUserInfo(String firstName, String lastName, int zipCode) {
        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName);
        postalCodeInput.setValue(String.valueOf(zipCode));
        buttonContinue.click();
        return Selenide.page(CheckOutPage.class);
    }

}
