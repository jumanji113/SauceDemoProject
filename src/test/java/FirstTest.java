
import constans.ItemConstants;
import lombok.SneakyThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.SignUpPage;

public class FirstTest extends BaseTest implements ItemConstants {

    private final static String expectedLogin = "standard_user";
    private final static String expectedPass = "secret_sauce";
    private final static String expectedFirstName = "Alexey";
    private final static String expectedLastName = "Jumanji";
    private final static int expectedZipCode = 214031;



    @Test
    @DisplayName("Вход на страницу, и ввод логина и пароля")
    @SneakyThrows
    public void openWebsite() {
        SignUpPage signUpPage = new SignUpPage();
        signUpPage.assertLoginAndPass(expectedLogin, expectedPass)
                .setData(expectedLogin, expectedPass)
                .clickButtonSignUp();
    }

    @Test
    @DisplayName("Проверка лого")
    public void checkLogo(){
        String expectedLogo = "Swag Labs";
        SignUpPage signUpPage = new SignUpPage();
        signUpPage
                .setData(expectedLogin, expectedPass)
                .clickButtonSignUp()
                .checkLogo(expectedLogo);
    }

    @Test
    @DisplayName("Проверка работы счетчика корзины")
    public void checkCounter(){
        SignUpPage signUpPage = new SignUpPage();
        signUpPage
                .setData(expectedLogin, expectedPass)
                .clickButtonSignUp()
                .checkInitialStateCart()
                .checkCounterCart(ItemConstants.BACKPACK)
                .checkCounterCart(ItemConstants.BIKE_LIGHT);
    }

    @Test
    @DisplayName("Проверка работы добавления элементов в корзину и проверка кол-ва элементов в корзине")
    public void checkAddItemToCart(){
        SignUpPage signUpPage = new SignUpPage();
        int expectedSizeCount = 3;
        signUpPage
                .setData(expectedLogin, expectedPass)
                .clickButtonSignUp()
                .addItemToCart(ItemConstants.BACKPACK)
                .addItemToCart(ItemConstants.BIKE_LIGHT)
                .addItemToCart(ItemConstants.ONESIE)
                .clickCartButton()
                .checkCountItems(3);
    }

    @Test
    @DisplayName("Проверка итоговой суммы товаров , а также и других окон, включая ввод данных пользователя")
    public void checkTotalSumm(){
        SignUpPage signUpPage = new SignUpPage();
        int expectedSizeCount = 3;
        signUpPage
                .setData(expectedLogin, expectedPass)
                .clickButtonSignUp()
                .addItemToCart(ItemConstants.BACKPACK)
                .addItemToCart(ItemConstants.BIKE_LIGHT)
                .addItemToCart(ItemConstants.ONESIE)
                .clickCartButton()
                .goToUserInfoPage()
                .setUserInfo(expectedFirstName, expectedLastName, expectedZipCode)
                .checkSummItems();
    }




}
