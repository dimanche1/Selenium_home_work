package app;

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

import page.*;

public class Application {
    public WebDriver driver;
    public WebDriverWait wait;

    private ProductsPage productsPage;
    private CheckoutPage checkoutPage;

    public Application() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        productsPage = new ProductsPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    public void addProductsToCart(int numberOfProductsToAdd){
        driver.get("http://localhost/litecart/en/rubber-ducks-c-1/");
        // Links to all products
        List<String> linksToProducts = productsPage.linksToProductsOnPage
                .stream().map(el -> el.getAttribute("href")).collect(Collectors.toList());

        // Number of items in the cart to be count in the following lambda function
        AtomicInteger itemsInCart = new AtomicInteger();
        itemsInCart.set(Integer.parseInt(productsPage.getCartElement.getText()));

        // Add products in the cart
        linksToProducts.forEach(link -> {
            if(itemsInCart.get() < numberOfProductsToAdd) {
                driver.get(link);
                productsPage.addProductButton.click();
                itemsInCart.addAndGet(1);
                wait.until(ExpectedConditions
                        .textToBePresentInElement(productsPage.numberOfItemsInCart, itemsInCart.toString()));
            }
        });
    }

    public void checkOut() {
        driver.findElement(By.cssSelector("a.link[href*=checkout]")).click();
    }

    public void deleteAllProductsInCart() {
        // Names of the products for checking
        List<String> namesOfProductsInCart = checkoutPage.nameListOfProductsInCart
                .stream().map(el -> el.getText())
                .collect(Collectors.toList());

        int allElmsButNotLast =namesOfProductsInCart.size() - 1;

        // Stop scrolling by clicking a product
        checkoutPage.firstProductsMiniPicture.click();
        // Deletes all thr products but leaves the last one for special treatment
        for (int i = 0; i < allElmsButNotLast; i++) {
            checkoutPage.firstProductsMiniPicture.click();

            wait.until(ExpectedConditions
                    .textToBePresentInElementLocated(By.cssSelector(String
                                    .format("div p a strong", namesOfProductsInCart.get(i))),
                            namesOfProductsInCart.get(i)));

            WebElement elToDelete = driver.findElement(By.xpath(String
                    .format("//td[contains(text(),'%s')]", namesOfProductsInCart.get(i))));

            checkoutPage.removeCartItemButton.click();

            wait.until(ExpectedConditions.stalenessOf(elToDelete));
        }

        // Delete last product
        wait.until(ExpectedConditions
                .textToBePresentInElementLocated(By.cssSelector(String
                                .format("div p a strong", namesOfProductsInCart.get(allElmsButNotLast))),
                        namesOfProductsInCart.get(allElmsButNotLast)));

        WebElement elToDelete = driver.findElement(By.xpath(String
                .format("//td[contains(text(),'%s')]", namesOfProductsInCart.get(allElmsButNotLast))));
        checkoutPage.removeCartItemButton.click();
        wait.until(ExpectedConditions.stalenessOf(elToDelete));
    }

    public void stop() {
        driver.quit();
        driver = null;
    }
}
