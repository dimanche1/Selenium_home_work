import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Exercise17 {
//    private EventFiringWebDriver driver;
    private  WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
//        driver = new EventFiringWebDriver(new ChromeDriver());
//        driver.register(new browserLogListener());
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testExercise17() {


        driver.get("http://localhost/litecart/admin/");

        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='doc=catalog']")));

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#content h1"), "Catalog"));

        List<String> links = driver.findElements(By.cssSelector("#content a:not([title=Edit])[href*=product_id]"))
                .stream()
                .map(el -> el.getAttribute("href"))
                .collect(Collectors.toList());


        links.forEach(link -> {
            driver.get(link);
            driver.manage().logs().get("browser").getAll().forEach(log -> {
                System.out.println(String.format("log after navigate to %s: %s", driver.getCurrentUrl(), log.getMessage()));
                Assert.fail("Browser's logs contains a message");
            });
        });

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

//    public static class browserLogListener extends AbstractWebDriverEventListener {
//
//        @Override
//        public void afterClickOn(WebElement element, WebDriver driver) {
//            driver.manage().logs().get("browser").filter(Level.ALL).forEach(log -> {
//                    System.out.println(String.format("log after navigate to %s: %s", driver.getCurrentUrl(), log));
//                    if (log.getLevel() == Level.SEVERE) {
//                        Assert.fail("There is error in browser's logs");
//                    }
//            });
//        }
//    }
}
