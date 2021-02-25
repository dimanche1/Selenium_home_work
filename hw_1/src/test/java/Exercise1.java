import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Exercise1 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void exercise1()
    {
        driver.get("https://www.google.com");
        driver.findElement(By.name("q")).sendKeys("selenium");
        driver.findElement(By.name("btnK")).click();
        wait.until(titleIs("selenium - Поиск в Google"));
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }

}
