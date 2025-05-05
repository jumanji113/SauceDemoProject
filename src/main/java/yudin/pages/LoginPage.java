package yudin.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPage {

    private static final String LOGIN = "standard_user";
    private static final String PASS = "secret_sauce";

    private SelenideElement allTextLogins = Selenide.$x("//div[@id='login_credentials']");
    private SelenideElement allFullPassword = Selenide.$x("//div[@data-test='login-password']");
    private SelenideElement loginInput = Selenide.$x("//input[@id='user-name']");
    private SelenideElement passwordInput = Selenide.$x("//input[@id='password']");
    private SelenideElement signUpButton = Selenide.$x("//input[@id='login-button']");

    private final static String BASE_URL = "https://www.saucedemo.com/";

    // Метод для открытия страницы
    @Step("Открытие страницы регистрации, Ввод данных и потверждение")
    public InventoryPage openPageAndSetDataLoginAndAccept() {
        open(BASE_URL);
        setData();
        clickButtonSignUp();
        return Selenide.page(InventoryPage.class);
    }

    @Step("Проверка данных для входа")
    public LoginPage assertLoginAndPass(String expectedLogin, String expectedPass) {
        String actualLogin = allTextLogins.getText().split("\n")[1].trim();
        String actualPass = allFullPassword.getText().split("\n")[1].trim();

        assertEquals(expectedLogin, actualLogin);
        assertEquals(expectedPass, actualPass);
        return this;
    }

    @Step("Ввод данных для регистрации")
    public LoginPage setData() {
        loginInput.setValue(LOGIN);
        passwordInput.setValue(PASS);
        return this;
    }

    @Step("Нажатие кнопки login")
    public InventoryPage clickButtonSignUp() {
        signUpButton.click();
        return Selenide.page(InventoryPage.class);
    }
}
