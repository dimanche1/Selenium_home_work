import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.StringSeqHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Exercise10 {
    private WebDriver driver;
    private WebDriverWait wait;
    // Chrome, Edge
    private final String Browser = "Edge";

    @Before
    public void start() {
        if (Browser.equals("Chrome")) {
            driver = new ChromeDriver();
        } else if (Browser.equals("Edge")) {
            System.setProperty("webdriver.edge.driver", "src//test//resources//msedgedriver.exe");
            driver = new EdgeDriver();
        }

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
//        assertEquals("Check line through regular price", "line-through", regularPrice.getCssValue("text-decoration-line"));
        assertEquals("Check line through regular price",
                "s", regularPrice.getTagName());
        assertTrue("RGB: all channels are equal",
                checkRGBChannelsEquality(regularPrice.getCssValue("text-decoration-color")));

        // Check campaign price
        WebElement campaignPrice = baseEl.findElement(By.cssSelector(".campaign-price"));
        duck.campaignPrice = campaignPrice.getText();
        assertTrue("RGB: some redness indeed", checkRGBChannelsRedness(campaignPrice.getCssValue("color")));
        assertEquals("Check boldness at campaign price", "strong", campaignPrice.getTagName());

         // Size comparison
        assertTrue("", (regularPrice.getSize().height < campaignPrice.getSize().height)
        && (regularPrice.getSize().width < campaignPrice.getSize().width));

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

    // Strings like "rgb(119, 119, 119)"
    private boolean checkRGBChannelsEquality(String rgb) {
        return rgb.substring(4,7).equals(rgb.substring(9,12)) &&
                rgb.substring(9,12).equals(rgb.substring(14,17));
    }

    // Strings like "r0.g1.b2.(3.119, 0, 0)"
    private boolean checkRGBChannelsRedness(String rgb) {
        List<String> sep_rgb = Arrays.asList(rgb.substring(4,17).replaceAll("\\s", "").split(","));
        return (Integer.parseInt(sep_rgb.get(1)) == Integer.parseInt(sep_rgb.get(2))) &&
                Integer.parseInt(sep_rgb.get(2)) == 0;
    }

    private float getOnlyFloatFromString(String str) {
        return Float.parseFloat(str.replaceAll("\\D+", ""));
    }
}


