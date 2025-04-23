
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static org.apache.commons.lang3.StringUtils.split;

public class FirstTest extends BaseTest{

    @Test
    @DisplayName("Вход на страницу, и ввод логина и пароля")
    public void openWebsite() throws InterruptedException {
        String expectedLogin = "standard_user";
        String expectedPass = "secret_sauce";
        open("https://www.saucedemo.com/");
        //получаем весь текст, который есть
        String fullText = $x("//div[@id='login_credentials']").getText();
        //разделяем его на нужный логин
        String login = fullText.split("\n")[1].trim();
        //получаем весь текст, который есть
        String fullPass = $x("//div[@data-test='login-password']").getText();
        //разделяем его на нужный логин
        String pass = fullPass.split("\n")[1].trim();
        //Assertions.assertEquals(expectedLogin, login);
        //Assertions.assertEquals(expectedPass, pass);

        //получаем инпут логина и вводим полученое значение выше
        $x("//input[@id='user-name']").setValue(login);
        //получаем инпут пароля и вводим полученое значение выше
        $x("//input[@id='password']").setValue(pass);

        //после ввода значений, нажимаем кнопку входа
        $x("//input[@id='login-button']").click();
    }
}
