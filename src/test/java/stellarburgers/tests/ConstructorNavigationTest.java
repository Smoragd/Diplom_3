package stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import stellarburgers.pom.MainPOM;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static stellarburgers.usefuldata.BrowserOptions.getWebDriver;
import static stellarburgers.usefuldata.UsefulData.BASE_URI;

 /*6.) Раздел «Конструктор»
        Проверь, что работают переходы к разделам:
            «Булки»,
            «Соусы»,
            «Начинки».*/

public class ConstructorNavigationTest {

    private WebDriver driver;
    MainPOM mainPOM;

    @Before
    public void setUp() {
        // Инициализация WebDriver
        driver = getWebDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(BASE_URI);
        //driver.manage().window().maximize();

        // Экземпляры классов page object
        mainPOM = new MainPOM(driver);
    }

    @Test
    @DisplayName("Проверка работы перехода к разделу 'Соусы'")
    public void jumpToSectionSauces(){

        mainPOM.clickSaucesButton();
        assertEquals("Не сработал переход к разделу Соусы", "Соусы", mainPOM.getButtonText());
    }

    @Test
    @DisplayName("Проверка работы перехода к разделу 'Начинки'")
    public void jumpToSectionFillings(){

        mainPOM.clickFillingsButton();
        assertEquals("Не сработал переход к разделу Начинки", "Начинки", mainPOM.getButtonText());
    }

    @Test
    @DisplayName("Проверка работы перехода к разделу 'Булки'")
    public void jumpToSectionBuns(){

        mainPOM.clickSaucesButton();
        mainPOM.clickBunsButton();
        assertEquals("Не сработал переход к разделу Булки", "Булки", mainPOM.getButtonText());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
