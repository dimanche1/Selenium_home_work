import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Exercise7 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testExercise7() {

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        int size = driver.findElements(By.cssSelector("li[id*=app-] >a")).size();
        if (size > 0) {
            for (int i = 1; i <= size; i++) {
                driver.findElement(By.cssSelector("li[id*=app-]:nth-child(" + i + ") >a")).click();
                assertTrue(TestBase.isElementPresent(driver, By.tagName("h1")));

                int sizeInnEls = driver.findElements(By.cssSelector("li[id*=doc-] >a")).size();
                if(sizeInnEls > 0) {
                    for (int j = 1; j <= sizeInnEls; j++){
                        driver.findElement(By.cssSelector("li[id*=doc-]:nth-child(" + j + ") >a")).click();
                        assertTrue(TestBase.isElementPresent(driver, By.tagName("h1")));
                    }
                }
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}

//public class Exercise7 {
//    private WebDriver driver;
//    private WebDriverWait wait;
//
//    @Before
//    public void start(){
//        driver = new ChromeDriver();
//// Set the driver path
////        System.setProperty("webdriver.edge.driver", "C://Distr//Selenium_drivers//msedgedriver.exe");
////        driver = new EdgeDriver();
//        wait = new WebDriverWait(driver, 10);
//
//    }
//
//    @Test
//    public void exercise7_login(){
////        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
////        login("admin", "admin");
////        try {
////            sleep(5000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////        exercise7_LeftMenuClicks();
//
//        driver.get("http://localhost/litecart/admin/");
//        driver.findElement(By.name("username")).sendKeys("admin");
//        driver.findElement(By.name("password")).sendKeys("admin");
//        driver.findElement(By.name("login")).click();
//
//        try {
//            sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        exercise7_LeftMenuClicks();
//    }
//
//    @Test
//    public void exercise7_LeftMenuClicks(){
//        List<WebElement> mainList = driver.findElements(By.cssSelector("li[id*=app-] >a"));
//        int size = mainList.size();
//        if (size > 0) {
//            for (int i = 1; i <= size; i++) {
////            li[id*=app-]:nth-child(0) >a
//                WebElement el = driver.findElement(By.cssSelector("li[id*=app-]:nth-child(" + i + ") >a"));
//                String elText = el.getText();
////                driver.findElement(By.cssSelector("li[id*=app-]:nth-child(" + i + ") >a")).click();
//                el.click();
//                try {
//                    sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
////                System.out.println(driver.findElement(By.tagName("h1")).getText());
////                assertNotNull(driver.findElement(By.tagName("h1")).getText());
//                assertNotEquals(driver.findElement(By.tagName("h1")).getText(),"");
////                System.out.println(driver.findElement(By.tagName("h1")).getText());
////                if (elText == driver.findElement(By.tagName("h1")).getText()){
////                    System.out.println("equal " + elText );
////                }
////                if(areElementsPresent(By.cssSelector("li[id*=doc-] >a"))){
////
////                }
//                List<WebElement> innerEls = driver.findElements(By.cssSelector("li[id*=doc-] >a"));
//                int sizeInnEls = innerEls.size();
//                if(sizeInnEls > 0) {
//                    for (int j = 1; j <= sizeInnEls; j++){
//                        driver.findElement(By.cssSelector("li[id*=doc-]:nth-child(" + j + ") >a")).click();
//                        try {
//                            sleep(3000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        System.out.println(driver.findElement(By.tagName("h1")).getText());
//                        assertNotEquals(driver.findElement(By.tagName("h1")).getText(),"");
//                    }
//                }
//            }
//        }
//
////        for (WebElement el: mainList) {
////            System.out.println(el.toString());
//////            el.click();
////            try {
////                sleep(1000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
//    }
//
//    boolean areElementsPresent(By locator) {
//        return driver.findElements(locator).size() > 0;
//    }
//
////    public void login(String login, String password){
////        driver.get("http://localhost/litecart/admin/");
////        driver.findElement(By.name("username")).sendKeys(login);
////        driver.findElement(By.name("password")).sendKeys(password);
////        driver.findElement(By.name("login")).click();
////    }
//
//    @After
//    public void stop(){
//        driver.quit();
//        driver = null;
//    }
//}