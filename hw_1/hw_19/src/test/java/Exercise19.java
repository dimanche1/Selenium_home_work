import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Exercise19 extends TestBase {

    @Test
    public void testExercise19()
    {
        app.addProductsToCart();

        app.checkOut();

        app.deleteAllProductsInCart();

        assertEquals("Expect: There are no items in your cart.",
                driver.findElement(By.cssSelector("div#checkout-cart-wrapper p em")).getText(),
                "There are no items in your cart.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
