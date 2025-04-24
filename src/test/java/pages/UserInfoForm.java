package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class UserInfoForm {

    private SelenideElement firstNameInput = $x("//input[@id = 'first-name']");
    private SelenideElement lastNameInput = $x("//input[@id = 'last-name']");
    private SelenideElement postalCodeInput = $x("//input[@id = 'postal-code']");
    private SelenideElement buttonContinue = $x("//input[@id = 'continue']");


    @Step("Заполнение данных пользователя")
    public CheckOutPage setUserInfo(String firstName, String lastName, int zipCode){
        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName);
        postalCodeInput.setValue(String.valueOf(zipCode));
        buttonContinue.click();
        return page(CheckOutPage.class);
    }

}
