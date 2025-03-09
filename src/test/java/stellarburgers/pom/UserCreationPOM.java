package stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserCreationPOM {

    private WebDriver driver;

    //Поля и локаторы:
    //поле для ввода имени
    private By inputName = By.xpath(".//label[contains(text(),'Имя')]/parent::div/input");
    //поле для ввода почты
    private By inputEmail = By.xpath(".//label[contains(text(),'Email')]/parent::div/input");
    //поле для ввода пароля
    private By inputPassword = By.xpath(".//label[contains(text(),'Пароль')]/parent::div/input");
    //кнопка Зарегистрироватся
    private By authButton = By.xpath(".//button[contains(text(),'Зарегистрироваться')]");
    //надпись Некорректный пароль
    private By incorrectPassText = By.xpath(".//p[contains(text(), 'Некорректный пароль')]");
    //кнопка Войти
    private By loginButton = By.xpath(".//a[contains(text(),'Войти')]");

    public UserCreationPOM(WebDriver driver) {
        this.driver = driver;
    }

    //Методы:

    @Step("заполнить поле 'Имя'")
    public void setInputName(String name) {
        driver.findElement(inputName).sendKeys(name);
    }

    @Step("заполнить поле 'Email'")
    public void setInputEmail(String email) {
        driver.findElement(inputEmail).sendKeys(email);
    }

    @Step("заполнить поле 'Пароль'")
    public void setInputPassword(String password) {
        driver.findElement(inputPassword).sendKeys(password);
    }

    @Step("кликнуть кнопку 'Зарегистрироваться'")
    public void clickAuthButton(){
        driver.findElement(authButton).isEnabled();
        driver.findElement(authButton).click();
    }

    @Step("заполнить поля и отправить форму регистрации")
    public void setInputData(String email, String password, String name){
        setInputEmail(email);
        setInputPassword(password);
        setInputName(name);
        clickAuthButton();
    }

    @Step("проверить, что надпись 'Некорректный пароль' отображается")
    public boolean isVisibleIncorrectPassText(){
        return driver.findElement(incorrectPassText).isDisplayed();
    }

    @Step("проверить доступность и кликнуть кнопку 'Войти'")
    public void clickLoginButton(){
        driver.findElement(loginButton).isEnabled();
        driver.findElement(loginButton).click();
    }

}
