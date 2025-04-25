
import yudin.constans.ItemConstants;
import lombok.SneakyThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yudin.pages.SignUpPage;

public class FirstTest extends BaseTest implements ItemConstants {

    //откорректировал названия final переменных
    private final static String LOGIN = "standard_user";
    private final static String PASS = "secret_sauce";
    private final static String FIRST_NAME = "Alexey";
    private final static String LAST_NAME = "Jumanji";
    private final static int ZIP_CODE = 214031;
    private SignUpPage signUpPage;

    //выносим инициализацию отдельно
    @BeforeEach
    public void signUpInit() {
        signUpPage = new SignUpPage();
    }

    @Test
    @DisplayName("Вход на страницу, и ввод логина и пароля")
    @SneakyThrows
    public void openWebsite() {
        signUpPage.assertLoginAndPass(LOGIN, PASS)
                .setData(LOGIN, PASS)
                .clickButtonSignUp();
    }

    @Test
    @DisplayName("Проверка лого")
    public void checkLogo() {
        String expectedLogo = "Swag Labs";
        signUpPage
                .setData(LOGIN, PASS)
                .clickButtonSignUp()
                .checkLogo(expectedLogo);
    }

    @Test
    @DisplayName("Проверка работы счетчика корзины")
    public void checkCounter() {
        signUpPage
                .setData(LOGIN, PASS)
                .clickButtonSignUp()
                .checkInitialStateCart()
                .checkCounterCart(ItemConstants.BACKPACK)
                .checkCounterCart(ItemConstants.BIKE_LIGHT);
    }

    @Test
    @DisplayName("Проверка работы добавления элементов в корзину и проверка кол-ва элементов в корзине")
    public void checkAddItemToCart() {
        int expectedSizeCount = 3;
        signUpPage
                .setData(LOGIN, PASS)
                .clickButtonSignUp()
                .addItemToCart(ItemConstants.BACKPACK)
                .addItemToCart(ItemConstants.BIKE_LIGHT)
                .addItemToCart(ItemConstants.ONESIE)
                .clickCartButton()
                .checkCountItems(3);
    }

    @Test
    @DisplayName("Проверка итоговой суммы товаров , а также и других окон, включая ввод данных пользователя")
    public void checkTotalSumm() {
        int expectedSizeCount = 3;
        signUpPage
                .setData(LOGIN, PASS)
                .clickButtonSignUp()
                .addItemToCart(ItemConstants.BACKPACK)
                .addItemToCart(ItemConstants.BIKE_LIGHT)
                .addItemToCart(ItemConstants.ONESIE)
                .clickCartButton()
                .goToUserInfoPage()
                .setUserInfo(FIRST_NAME, LAST_NAME, ZIP_CODE)
                .checkSummItems();
    }

}
