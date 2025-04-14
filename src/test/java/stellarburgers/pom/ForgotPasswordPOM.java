package stellarburgers.pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPOM {

    private WebDriver driver;

    //Поля и локаторы:
    //кнопка Войти
    private By loginButton = By.xpath(".//a[contains(text(),'Войти')]");

    public ForgotPasswordPOM(WebDriver driver) {
        this.driver = driver;
    }

    //Методы:
    @Step("проверить доступность и кликнуть кнопку 'Войти'")
    public void clickLoginButton(){
        driver.findElement(loginButton).isEnabled();
        driver.findElement(loginButton).click();
    }
}
