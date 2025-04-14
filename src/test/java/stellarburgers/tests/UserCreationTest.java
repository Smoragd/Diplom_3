package stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import stellarburgers.pom.*;
import java.util.concurrent.TimeUnit;
import static stellarburgers.usefuldata.BrowserOptions.getWebDriver;
import static stellarburgers.usefuldata.UsefulData.*;
import static stellarburgers.usefuldata.UserAPI.*;

 /*1.) Регистрация
        Проверь:
            Успешную регистрацию.
            Ошибку для некорректного пароля. Минимальный пароль — шесть символов.*/

public class UserCreationTest {

    private WebDriver driver;

    private String login;
    private String password6; // пароль 6 символов
    private String password5; // пароль 5 символов - некорректный
    private String firstName;
    MainPOM mainPOM;
    UserLoginPOM userLoginPOM;
    UserCreationPOM userCreationPOM;

    @Before
    public void setUp() {
        // Инициализация WebDriver
        driver = getWebDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(BASE_URI);
        //driver.manage().window().maximize();

        // Генерация данных для юзера
        login = generateRandomLogin();
        password6 = generateRandomPassword6();
        password5 = generateRandomPassword5();
        firstName = generateRandomFirstName();
        RestAssured.baseURI = BASE_URI;

        // Экземпляры классов page object
        mainPOM = new MainPOM(driver);
        userLoginPOM = new UserLoginPOM(driver);
        userCreationPOM = new UserCreationPOM(driver);
    }

    @Test
    @DisplayName("Проверка успешной регистрации")
    public void checkSuccessfulRegistrationTest(){

        mainPOM.clickPersonalAccountButton();
        userLoginPOM.clickRegisterButton();
        userCreationPOM.setInputData(login,password6, firstName);
        userLoginPOM.waitLoadingLoginPage();

        Assert.assertTrue(userLoginPOM.checkLoadingLoginPage());

        // Авторизация созданного юзера (для получения accessToken) + удаление созданного юзера
        loginUser(login, password6);
        deleteUser(accessToken);
    }

    @Test
    @DisplayName("Проверка вывода ошибки для некорректного пароля менее 6 символов")
    public void checkBadPasswordErrorRegistrationTest(){

        mainPOM.clickPersonalAccountButton();
        userLoginPOM.clickRegisterButton();
        userCreationPOM.setInputData(login, password5, firstName);
        userLoginPOM.waitLoadingLoginPage();

        Assert.assertTrue(userCreationPOM.isVisibleIncorrectPassText());

        // Удаление юзера не производится, тк юзер НЕ создан с паролем короче 6 символов
    }

    @After
    public void tearDown() { driver.quit(); }

}
