import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Exercise14 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testExercise14() {
        driver.get("http://localhost/litecart/admin/");

        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='doc=countries']")));

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#content a.button")));

        driver.findElement(By.cssSelector("#content a.button")).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#content h1"), "Add New Country"));

        List<WebElement> externalLinks = driver
                .findElements(By.xpath("//a[contains(@target, '_blank')][./i[contains(@class, 'fa fa-external-link')]]"));

        String originalWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();

        externalLinks.forEach(el -> {
            el.click();
            String newWindow = wait.until(anyWindowOtherThan(existingWindows));
            driver.switchTo().window(newWindow);
            // wait until a page is loaded
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState=='complete'"));
            driver.close();
            driver.switchTo().window(originalWindow);
        });
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }
}
