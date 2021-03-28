import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Exercise12 {
    private WebDriver driver;
    private WebDriverWait wait;
    final int TIME_WAIT = 2000;
    final int productsToCreate = 10;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testExercise12() {

        loginToAdminPanel();

        for(int i = 0; i < productsToCreate; i++) {
            createProduct();
        }
    }

    public void loginToAdminPanel() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    public void createProduct() {

        String UUID = java.util.UUID.randomUUID().toString();
        String productName = "yellow_duck_" + UUID;
        String procductCode = "code_" + UUID;

        // Add new product
        wait(TIME_WAIT);
        driver.findElement(By.cssSelector("a[href*=catalog]")).click();
        wait(TIME_WAIT);
        driver.findElement(By.cssSelector("a[href*=edit_product]")).click();
        wait(TIME_WAIT);

        // General tab
        driver.findElement(By.cssSelector("input[name=status][value='1']")).click();
        driver.findElement(By.cssSelector("input[name='name[en]']")).sendKeys(productName);
        driver.findElement(By.cssSelector("input[name=code]")).sendKeys(procductCode);
        driver.findElement(By.cssSelector("input[value='1'][data-name='Rubber Ducks']")).click();
        selectHandle(By.cssSelector("select[name=default_category_id]"), "Rubber Ducks");
        WebElement quantityElm =  driver.findElement(By.cssSelector("input[name=quantity"));
        quantityElm.clear();
        quantityElm.sendKeys("100");
        File imgToAttach = null;
        try {
            imgToAttach = new File(getClass().getClassLoader().getResource("yellow_duck.png").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector("input[type=file][name='new_images[]']"))
                .sendKeys(imgToAttach.getAbsolutePath());
        SimpleDateFormat dtf = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        driver.findElement(By.cssSelector("input[name=date_valid_from]"))
                .sendKeys(dtf.format(date));
        driver.findElement(By.cssSelector("input[name=date_valid_to]"))
                .sendKeys("31.12.2021");

        // Information tab
        driver.findElement(By.cssSelector("a[href='#tab-information']")).click();
        wait(TIME_WAIT);
        selectHandle(By.cssSelector("select[name=manufacturer_id]"), "ACME Corp.");
        driver.findElement(By.cssSelector("input[name=keywords]")).sendKeys("duck yellow");
        driver.findElement(By.cssSelector("input[name='short_description[en]']")).sendKeys("Yellow  Duck");
        driver.findElement(By.cssSelector(".trumbowyg-editor")).sendKeys("Some long description");
        driver.findElement(By.cssSelector("input[name='head_title[en]']")).sendKeys("Yellow  Duck");
        driver.findElement(By.cssSelector("input[name='meta_description[en]']")).sendKeys("Some meta description");

        // Prices tab
        driver.findElement(By.cssSelector("a[href='#tab-prices']")).click();
        wait(TIME_WAIT);
        WebElement priceElm = driver.findElement(By.cssSelector("input[name=purchase_price]"));
        priceElm.clear();
        priceElm.sendKeys("1");
        selectHandle(By.cssSelector("select[name=purchase_price_currency_code]"), "US Dollars");
        driver.findElement(By.cssSelector("input[name='prices[USD]']")).sendKeys("2");
        driver.findElement(By.cssSelector("input[name='prices[EUR]']")).sendKeys("1.8");

        // Save
        driver.findElement(By.cssSelector("button[name=save]")).click();
        wait(TIME_WAIT);

        // Check
        List<WebElement> products = new ArrayList<>();
        products = driver.findElements(By.cssSelector("form[name=catalog_form] .dataTable tr.row a:not([title=Edit])"));
        products.forEach(el -> {
            if (el.getText().equals(productName)) {
                Product.isCreated = true;
            }
        });

        assertTrue("Product was created", Product.isCreated);

        wait(TIME_WAIT);
    }

    public void wait(int inMillis) {
        try {
            Thread.sleep(inMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectHandle(By locator, String textToSelect) {
        Select select = new Select(driver.findElement(locator));
        select.selectByVisibleText(textToSelect);
    }

    static class Product {
        private static boolean isCreated = false;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
