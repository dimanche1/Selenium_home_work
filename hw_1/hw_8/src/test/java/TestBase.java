import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestBase {
//    private static WebDriver driver;
//    private static WebDriverWait wait;

//    @Before
//    public static WebDriver start() {
//        driver = new ChromeDriver();
//        wait = new WebDriverWait(driver, 10);
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
//        return driver;
//    }

    public static boolean areElementsPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    public static int numberOfElements(WebDriver driver, By locator) {
        return driver.findElements(locator).size();
    }

    public static boolean isElementPresent(WebDriver driver, By locator) {

        try {
            driver.findElement(locator);
            return true;
        }
        catch (InvalidSelectorException e) {
            throw e;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}
