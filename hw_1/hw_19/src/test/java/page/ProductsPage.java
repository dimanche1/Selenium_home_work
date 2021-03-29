package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductsPage extends Page {

    public ProductsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css="ul.listing-wrapper.products li.product a.link")
    public List<WebElement> linksToProductsOnPage;

    @FindBy(css="div#cart span.quantity")
    public WebElement getCartElement;

    @FindBy(css="button[name=add_cart_product]")
    public WebElement addProductButton;

    @FindBy(css="div#cart span.quantity")
    public  WebElement numberOfItemsInCart;
}
