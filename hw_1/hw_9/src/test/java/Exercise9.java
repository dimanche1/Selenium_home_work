import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class Exercise9 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    private void login() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void testExercise9_1() {

        login();

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        List<WebElement> countries = driver.findElements(By.cssSelector("form[name=countries_form] tr.row"));
        List<String> countriesNames = new ArrayList<>();
        countries.forEach(el -> countriesNames.add(el.findElement(By.cssSelector("td a:not([title])")).getText()));
//        check guava
//        Collections.sort(countriesNames, Collections.reverseOrder());
        assertTrue(TestBase.isSortedNaturally(countriesNames));

        List<String> countriesWithZones = new ArrayList<>();
        countries.forEach(el -> {
                    if (Integer.parseInt(el.findElement(By.cssSelector("td:nth-child(6)")).getText()) > 0) {
                        countriesWithZones.add(el.findElement(By.cssSelector("td a:not([title])")).getAttribute("href"));
                    }
                });

        if (countriesWithZones.size() > 0) {
            countriesWithZones.forEach(el -> {
                driver.get(el);
                List<String> zoneNames = new ArrayList<>();
                driver.findElements(By.cssSelector("#table-zones tr td:nth-child(3) input[name*=zones]"))
                    .forEach(zone_el -> zoneNames.add(zone_el.getText()));
                assertTrue(TestBase.isSortedNaturally(zoneNames));
            });
        }
    }

    @Test
    public void testExercise9_2() {

        login();

        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");

        List <String> geoZones = new ArrayList<>();
        driver.findElements(By.cssSelector("form[name=geo_zones_form] tr.row td:nth-child(3) a"))
                .forEach(el -> geoZones.add(el.getAttribute("href")));

        if(geoZones.size() > 0) {
            geoZones.forEach(zone -> {
                driver.get(zone);
                List<String> zoneNames = new ArrayList<>();
                driver.findElements(By.cssSelector("#table-zones td:nth-child(3) select [selected=selected]"))
                        .forEach(zone_el -> zoneNames.add(zone_el.getText()));
                assertTrue(TestBase.isSortedNaturally(zoneNames));
            });
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
