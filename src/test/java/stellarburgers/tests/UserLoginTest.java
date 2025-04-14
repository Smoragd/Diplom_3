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

 /*2.) Вход
    Проверь:
            вход по кнопке «Войти в аккаунт» на главной,
            вход через кнопку «Личный кабинет»,
            вход через кнопку в форме регистрации,
            вход через кнопку в форме восстановления пароля.*/

public class UserLoginTest {

    private WebDriver driver;

    private String login;
    private String password6; // пароль 6 символов
    private String firstName;
    MainPOM mainPOM;
    UserLoginPOM userLoginPOM;
    AccountPOM accountPOM;
    UserCreationPOM userCreationPOM;
    ForgotPasswordPOM forgotPasswordPOM;

    @Before
    public void setUp() {
        // Инициализация WebDriver
        driver = getWebDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(BASE_URI);
        //driver.manage().window().maximize();

        // Создание юзера и получение его accessToken
        login = generateRandomLogin();
        password6 = generateRandomPassword6();
        firstName = generateRandomFirstName();
        RestAssured.baseURI = BASE_URI;
        createUser(login, password6, firstName);

        // Экземпляры классов page object
        mainPOM = new MainPOM(driver);
        userLoginPOM = new UserLoginPOM(driver);
        accountPOM = new AccountPOM(driver);
        userCreationPOM = new UserCreationPOM(driver);
        forgotPasswordPOM = new ForgotPasswordPOM(driver);
    }

    @Test
    @DisplayName("Проверка входа по кнопке 'Войти в аккаунт' на главной странице")
    public void loginWithLoginAccountButtonTest(){

        mainPOM.clickLoginAccountButton();
        userLoginPOM.setInputData(login, password6);
        mainPOM.waitAuthorization();

        Assert.assertTrue(mainPOM.isCreateOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка входа через кнопку 'Личный кабинет'")
    public void loginWithPersonalAccountButtonTest(){

        mainPOM.clickPersonalAccountButton();
        userLoginPOM.setInputData(login, password6);
        mainPOM.waitAuthorization();

        Assert.assertTrue(mainPOM.isCreateOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка входа через кнопку в форме регистрации")
    public void loginWithRegistrationFormLoginButtonTest(){

        mainPOM.clickPersonalAccountButton();
        userLoginPOM.clickRegisterButton();
        userCreationPOM.clickLoginButton();
        userLoginPOM.setInputData(login, password6);
        mainPOM.waitAuthorization();

        Assert.assertTrue(mainPOM.isCreateOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка входа через кнопку в форме восстановления пароля")
    public void loginWithPasswordRecoveryButtonTest(){

        mainPOM.clickPersonalAccountButton();
        userLoginPOM.clickPasswordRecoveryButton();
        forgotPasswordPOM.clickLoginButton();
        userLoginPOM.setInputData(login, password6);
        mainPOM.waitAuthorization();

        Assert.assertTrue(mainPOM.isCreateOrderButtonVisible());
    }


    @After
    public void tearDown() {
        driver.quit();
        deleteUser(accessToken);
    }

}
