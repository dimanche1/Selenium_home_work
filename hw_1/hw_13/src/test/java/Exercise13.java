import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Exercise13 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testExercise13() {

        driver.get("http://localhost/litecart/en/rubber-ducks-c-1/");

        // Links to all products
        List <String> linksToProducts = driver.
                findElements(By.cssSelector("ul.listing-wrapper.products li.product a.link"))
                .stream().map(el -> el.getAttribute("href")).collect(Collectors.toList());

        // Number of items in the cart to be count in the following lambda function
        AtomicInteger itemsInCart = new AtomicInteger();
        itemsInCart.set(Integer.parseInt(driver.findElement(By.cssSelector("div#cart span.quantity")).getText()));

        // Add products in the cart
        linksToProducts.forEach(link -> {
                  if(itemsInCart.get() < 4) {
                    driver.get(link);
                    driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();
                    itemsInCart.addAndGet(1);
                    wait.until(ExpectedConditions
                            .textToBePresentInElementLocated(By.cssSelector("div#cart span.quantity"),
                                    itemsInCart.toString()));
                }
        });

        // Checkout
        driver.findElement(By.cssSelector("a.link[href*=checkout]")).click();

        // Names of the products for checking
        List<String> namesOfProductsInCart = driver.findElements(By
                .cssSelector("div#order_confirmation-wrapper tr td.item"))
                .stream().map(el -> el.getText())
                .collect(Collectors.toList());

        int allElmsButNotLast =namesOfProductsInCart.size() - 1;

        // Deletes all thr products but leaves the last one for special treatment
        for (int i = 0; i < allElmsButNotLast; i++) {
            driver.findElement(By.cssSelector("div#box-checkout-cart ul.shortcuts a")).click();
            wait.until(ExpectedConditions
                            .textToBePresentInElementLocated(By.cssSelector(String
                                    .format("div p a strong", namesOfProductsInCart.get(i))),
                                    namesOfProductsInCart.get(i)));
            driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
            wait.until(ExpectedConditions.invisibilityOfElementWithText
                    (By.cssSelector("div#order_confirmation-wrapper tr td.item"), namesOfProductsInCart.get(i)));
        }

        // Delete last product
        wait.until(ExpectedConditions
                .textToBePresentInElementLocated(By.cssSelector(String
                                .format("div p a strong", namesOfProductsInCart.get(allElmsButNotLast))),
                        namesOfProductsInCart.get(allElmsButNotLast)));
        driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
        wait.until(ExpectedConditions.invisibilityOfElementWithText
                (By.cssSelector("div#order_confirmation-wrapper tr td.item"), namesOfProductsInCart.get(allElmsButNotLast)));

        assertEquals("Expect: There are no items in your cart.",
                driver.findElement(By.cssSelector("div#checkout-cart-wrapper p em")).getText(),
               "There are no items in your cart.");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}

//        $$("div#order_confirmation-wrapper tr td.item")
//        $$("div p a[href*='blue-duck-p-4'] strong")
//        $$("div#box-checkout-cart ul.shortcuts a")
//        linksToProducts = null;
//        linksToProducts = driver.
//                findElements(By.cssSelector("ul.listing-wrapper.products li.product a.link"))
//                .stream().map(el -> el.getAttribute("href")).collect(Collectors.toList());
//        linksToProducts.forEach(link -> {
//            driver.get(link);
//            driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
//        });

//        int numberOfProductsInCart = driver.findElements(By.cssSelector("div#box-checkout-cart ul.shortcuts a")).size();
//        for (int ) {
//
//        }

//        driver.findElements(By.cssSelector("div#box-checkout-cart ul.shortcuts a"))
//                .forEach(el -> {
//                    driver.findElement(By.cssSelector("div#box-checkout-cart ul.shortcuts a")).click();
//                    driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
////                    $$("div p a[href*='blue-duck-p-4'] strong")
//                    wait.until(ExpectedConditions
//                            .textToBePresentInElementLocated(By.cssSelector(String.format("", ))));
////                    el.click();
////                    driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
//                });