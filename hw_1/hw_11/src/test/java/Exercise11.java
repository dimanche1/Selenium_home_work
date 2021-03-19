import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Exercise11 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testExercise11() {
        driver.get("http://localhost/litecart/");

        String FirstName = String.format("user_%s", UUID.randomUUID().toString());
        String LastName = "LastName";
        String address = "Some St. 999";
        String postcode = "12345";
        String city = "Alabama city";
        String email = String.format("%s@gmail.com", FirstName);
        String phone = "1112233";
        String password = "zxc890";

        // Register new customer
        driver.findElement(By.cssSelector("#box-account-login a[href*=create_account]")).click();

        assertTrue("Create user form is present", TestBase.isElementPresent(driver, By.cssSelector("#create-account")));

        driver.findElement(By.cssSelector("input[name=firstname]")).sendKeys(FirstName);
        driver.findElement(By.name("lastname")).sendKeys(LastName);
        driver.findElement(By.name("address1")).sendKeys(address);
        driver.findElement(By.name("postcode")).sendKeys(postcode);
        driver.findElement(By.name("city")).sendKeys(city);

        driver.findElement(By.cssSelector(".select2-selection__arrow")).click();
        WebElement searchField = driver.findElement(By.cssSelector("input.select2-search__field"));
        searchField.sendKeys("United States");
        searchField.sendKeys(Keys.ENTER);

        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("phone")).sendKeys(phone);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("confirmed_password")).sendKeys(password);

        driver.findElement(By.name("create_account")).click();

        assertTrue("Check success of user creation",
                TestBase.isElementPresent(driver, By.cssSelector(".notice.success")));

        // logout
        driver.findElement(By.cssSelector("a[href*=logout]")).click();

        //login
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();

        assertTrue("Check success of user login",
                TestBase.isElementPresent(driver, By.cssSelector(".notice.success")));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
