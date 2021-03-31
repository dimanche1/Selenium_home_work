package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage extends Page {

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css="div#order_confirmation-wrapper tr td.item")
    public List<WebElement> nameListOfProductsInCart;

    @FindBy(css="div#box-checkout-cart ul.shortcuts a")
    public WebElement firstProductsMiniPicture;

    @FindBy(css="button[name=remove_cart_item]")
    public  WebElement removeCartItemButton;

    @FindBy(css="div#checkout-cart-wrapper p em")
    public  WebElement containerForMsgOfEmptiness;
}
