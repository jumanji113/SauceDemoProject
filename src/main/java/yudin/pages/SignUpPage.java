package yudin.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.open;

public class SignUpPage {

    private SelenideElement allTextLogins = Selenide.$x("//div[@id='login_credentials']");
    private SelenideElement allFullPassword = Selenide.$x("//div[@data-test='login-password']");
    private SelenideElement loginInput = Selenide.$x("//input[@id='user-name']");
    private SelenideElement passwordInput = Selenide.$x("//input[@id='password']");
    private SelenideElement signUpButton = Selenide.$x("//input[@id='login-button']");

    private final static String BASE_URL = "https://www.saucedemo.com/";

    public SignUpPage() {
        openPage();
    }

    // Метод для открытия страницы
    @Step("Открытие страницы регистрации")
    private void openPage() {
        Selenide.open(BASE_URL);
    }

    @Step("Проверка данных для входа")
    public SignUpPage assertLoginAndPass(String expectedLogin, String expectedPass) {
        String actualLogin = allTextLogins.getText().split("\n")[1].trim();
        String actualPass = allFullPassword.getText().split("\n")[1].trim();

        Assertions.assertEquals(expectedLogin, actualLogin);
        Assertions.assertEquals(expectedPass, actualPass);
        return this;
    }

    @Step("Ввод данных для регистрации")
    public SignUpPage setData(String expectedLogin, String expectedPass) {
        loginInput.setValue(expectedLogin);
        passwordInput.setValue(expectedPass);
        return this;
    }

    @Step("Нажатие кнопки login")
    public InventoryPage clickButtonSignUp() {
        signUpButton.click();
        return Selenide.page(InventoryPage.class);
    }

}
