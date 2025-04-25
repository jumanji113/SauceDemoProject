package yudin.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class SignUpPage {

    private SelenideElement allTextLogins = Selenide.$x("//div[@id='login_credentials']");
    private SelenideElement allFullPassword = Selenide.$x("//div[@data-test='login-password']");
    private SelenideElement loginInput = Selenide.$x("//input[@id='user-name']");
    private SelenideElement passwordInput = Selenide.$x("//input[@id='password']");
    private SelenideElement signUpButton = Selenide.$x("//input[@id='login-button']");

    private String actualLogin = allTextLogins.getText().split("\n")[1].trim();
    private String actualPass = allFullPassword.getText().split("\n")[1].trim();

    @Step("Проверка данных для входа")
    public SignUpPage assertLoginAndPass(String expectedLogin, String expectedPass) {
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
    public MainPage clickButtonSignUp() {
        signUpButton.click();
        return Selenide.page(MainPage.class);
    }

}
