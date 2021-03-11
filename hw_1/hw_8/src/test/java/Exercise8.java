import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.Locator;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class Exercise8 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testExercise8() {

        driver.get("http://localhost/litecart/en/rubber-ducks-c-1/");

        int size = driver.findElements(By.cssSelector("ul.products >li")).size();
        if (size > 0) {
            for (int i = 1; i <= size; i++) {
                By locator = By.cssSelector("ul.products li.product:nth-child(" + i + ") a[title*='Duck'] .sticker");
                assertTrue(TestBase.isElementPresent(driver, locator) && (TestBase.numberOfElements(driver, locator) == 1));
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
