import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class TestBase {

    public static boolean areElementsPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
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
