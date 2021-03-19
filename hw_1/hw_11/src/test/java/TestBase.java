import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class TestBase {

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

    public static boolean isSortedNaturally(List<String> listOfStrings) {
        return Ordering.<String> natural().isOrdered(listOfStrings);
    }
}
