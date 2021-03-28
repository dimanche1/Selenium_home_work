import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage {
    private WebDriver driver;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> linksToProductsOnPage() {
        return driver.findElements(By.cssSelector("ul.listing-wrapper.products li.product a.link"));
    }

    public  WebElement getCartElement() { return driver.findElement(By.cssSelector("div#cart span.quantity")); }
}
