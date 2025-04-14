package stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import stellarburgers.pom.AccountPOM;
import stellarburgers.pom.MainPOM;
import stellarburgers.pom.UserLoginPOM;
import java.util.concurrent.TimeUnit;
import static stellarburgers.usefuldata.BrowserOptions.getWebDriver;
import static stellarburgers.usefuldata.UsefulData.*;
import static stellarburgers.usefuldata.UsefulData.BASE_URI;
import static stellarburgers.usefuldata.UserAPI.*;
import static stellarburgers.usefuldata.UserAPI.accessToken;

/*3.) Переход в личный кабинет
        Проверь переход по клику на «Личный кабинет».
    4.) Переход в конструктор:
        - Проверь переход из личного кабинета по клику на «Конструктор»
        - и из личного кабинета на логотип Stellar Burgers.
    5.) Выход из аккаунта
        Проверь выход по кнопке «Выйти» в личном кабинете.*/

public class AccountNavigationTest {

    private WebDriver driver;

    private String login;
    private String password6; // пароль 6 символов
    private String firstName;
    MainPOM mainPOM;
    UserLoginPOM userLoginPOM;
    AccountPOM accountPOM;

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

        // Авторизация перед тестом
        mainPOM.clickLoginAccountButton();
        userLoginPOM.setInputData(login, password6);
        mainPOM.waitAuthorization();
    }

    @Test
    @DisplayName("Проверка перехода в Личный кабинет по клику на кнопку 'Личный кабинет'")
    public void followPersonalAccountButtonTest() {

        mainPOM.clickPersonalAccountButton();
        accountPOM.waitLoadingProfilePage();

        Assert.assertTrue(accountPOM.checkLoadingProfilePage());
    }

    @Test
    @DisplayName("Проверка перехода из Личного кабинета на Главную страницу по клику на кнопку 'Конструктор'")
    public void followConstructorButtonTest(){

        mainPOM.clickPersonalAccountButton();
        accountPOM.waitLoadingProfilePage();
        accountPOM.clickConstructorButton();
        mainPOM.waitLoadingMainPage();

        Assert.assertTrue(mainPOM.isAssembleBurgerTextVisible());
    }

    @Test
    @DisplayName("Проверка перехода из Личного кабинета на Главную страницу по клику на логотип")
    public void followLogoTest(){

        mainPOM.clickPersonalAccountButton();
        accountPOM.waitLoadingProfilePage();
        accountPOM.clickLogo();
        mainPOM.waitLoadingMainPage();

        Assert.assertTrue(mainPOM.isAssembleBurgerTextVisible());
    }

    @Test
    @DisplayName("Проверка выхода из профиля по кнопке 'Выйти' в Личном кабинете")
    public void followExitButtonTest(){

        mainPOM.clickPersonalAccountButton();
        accountPOM.waitLoadingProfilePage();
        accountPOM.clickExitButton();
        userLoginPOM.waitLoadingLoginPage();

        Assert.assertTrue(userLoginPOM.checkLoadingLoginPage());
    }

    @After
    public void tearDown() {
        driver.quit();
        deleteUser(accessToken);
    }
}
