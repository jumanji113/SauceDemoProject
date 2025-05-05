package yudin.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class UserInfoForm {

    private static final String FIRST_NAME = "Alexey";
    private static final String LAST_NAME = "Jumanji";
    private static final int ZIP_CODE = 214031;

    private SelenideElement firstNameInput = Selenide.$x("//input[@id = 'first-name']");
    private SelenideElement lastNameInput = Selenide.$x("//input[@id = 'last-name']");
    private SelenideElement postalCodeInput = Selenide.$x("//input[@id = 'postal-code']");
    private SelenideElement buttonContinue = Selenide.$x("//input[@id = 'continue']");

    @Step("Заполнение данных пользователя")
    public CheckStepTwo setUserInfo() {
        firstNameInput.setValue(FIRST_NAME);
        lastNameInput.setValue(LAST_NAME);
        postalCodeInput.setValue(String.valueOf(ZIP_CODE));
        buttonContinue.click();
        return Selenide.page(CheckStepTwo.class);
    }
}
