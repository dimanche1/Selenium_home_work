import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Application extends TestBase{
    public void addProductsToCart(){
        driver.get("http://localhost/litecart/en/rubber-ducks-c-1/");
        // Links to all products
        List<String> linksToProducts = driver.
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
    }

    public void checkOut() {
        driver.findElement(By.cssSelector("a.link[href*=checkout]")).click();
    }

    public void deleteAllProductsInCart() {
        // Names of the products for checking
        List<String> namesOfProductsInCart = driver.findElements(By
                .cssSelector("div#order_confirmation-wrapper tr td.item"))
                .stream().map(el -> el.getText())
                .collect(Collectors.toList());

        int allElmsButNotLast =namesOfProductsInCart.size() - 1;

        // Stop scrolling by clicking a product
        driver.findElement(By.cssSelector("div#box-checkout-cart ul.shortcuts a")).click();
        // Deletes all thr products but leaves the last one for special treatment
        for (int i = 0; i < allElmsButNotLast; i++) {
            driver.findElement(By.cssSelector("div#box-checkout-cart ul.shortcuts a")).click();

            wait.until(ExpectedConditions
                    .textToBePresentInElementLocated(By.cssSelector(String
                                    .format("div p a strong", namesOfProductsInCart.get(i))),
                            namesOfProductsInCart.get(i)));

            WebElement elToDelete = driver.findElement(By.xpath(String
                    .format("//td[contains(text(),'%s')]", namesOfProductsInCart.get(i))));

            driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();

            wait.until(ExpectedConditions.stalenessOf(elToDelete));
        }

        // Delete last product
        wait.until(ExpectedConditions
                .textToBePresentInElementLocated(By.cssSelector(String
                                .format("div p a strong", namesOfProductsInCart.get(allElmsButNotLast))),
                        namesOfProductsInCart.get(allElmsButNotLast)));

        WebElement elToDelete = driver.findElement(By.xpath(String
                .format("//td[contains(text(),'%s')]", namesOfProductsInCart.get(allElmsButNotLast))));
        driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
        wait.until(ExpectedConditions.stalenessOf(elToDelete));
    }
}
