package test;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertEquals;

public class Exercise19 extends TestBase {

    @Test
    public void testExercise19()
    {
        app.addProductsToCart(4);

        app.checkOut();

        app.deleteAllProductsInCart();

        Assert.assertEquals("Expect: There are no items in your cart.",
                app.driver.findElement(By.cssSelector("div#checkout-cart-wrapper p em")).getText(),
                "There are no items in your cart.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
