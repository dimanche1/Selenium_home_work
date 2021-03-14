import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Exercise10 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    private void login() {
        driver.get("http://localhost/litecart/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void testExercise10() {
        driver.get("http://localhost/litecart/");

        // Main page duck
        WebElement elOnMainPage = driver.findElement(By.cssSelector("#box-campaigns a:nth-child(1)"));
        Duck duckOnMainPage = checkAndReturnDuck(elOnMainPage);
        elOnMainPage.click();

        // Subcategory page duck
        WebElement elOnSubctgrPage = driver.findElement(By.cssSelector("#box-product"));
        Duck duckOnSubctgrPage = checkAndReturnDuck(elOnSubctgrPage);

        //are ducks equal?
        assertTrue(duckOnMainPage.equals(duckOnSubctgrPage));
    }

    private Duck checkAndReturnDuck(WebElement baseEl) {
        Duck duck = new Duck();

        try {
            duck.name = baseEl.findElement(By.cssSelector(".name")).getText();
        } catch (NoSuchElementException e) {
            duck.name = baseEl.findElement(By.cssSelector("#box-product h1.title")).getText();
        }

        // Check regular price
        WebElement regularPrice = baseEl.findElement(By.cssSelector(".regular-price"));
        duck.regularPrice = regularPrice.getText();
        assertEquals("line-through", regularPrice.getCssValue("text-decoration-line"));
        assertTrue("RGB: all channels are equal",
                checkRGBChannelsEquality(regularPrice.getCssValue("text-decoration-color")));

        // Check campaign price
        WebElement campaignPrice = baseEl.findElement(By.cssSelector(".campaign-price"));
        duck.campaignPrice = campaignPrice.getText();

        assertTrue("The regular price is less than campaign one",
                Integer.parseInt(regularPrice.getCssValue("font-weight")) <
                        Integer.parseInt(campaignPrice.getCssValue("font-weight")));

        return duck;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    class Duck {
        private String name, regularPrice, campaignPrice;

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof Duck)) {
                return false;
            }

            Duck duck = (Duck) o;

            return this.name.equals(duck.name) && this.regularPrice.equals(duck.regularPrice) &&
                    this.campaignPrice.equals(duck.campaignPrice);
        }
    }

    // Strings like rgb(119, 119, 119)
    private boolean checkRGBChannelsEquality(String rgb) {
        return rgb.substring(4,7).equals(rgb.substring(9,12)) &&
                rgb.substring(9,12).equals(rgb.substring(14,17));
    }
}


