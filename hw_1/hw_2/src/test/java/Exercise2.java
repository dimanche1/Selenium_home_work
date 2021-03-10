import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Exercise2 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
//        driver = new ChromeDriver();
        // Set the driver path
        System.setProperty("webdriver.edge.driver", "C://Distr//Selenium_drivers//msedgedriver.exe");
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void exercise2()
    {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
