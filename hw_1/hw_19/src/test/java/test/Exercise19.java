package test;

import org.junit.Assert;
import org.junit.Test;

public class Exercise19 extends TestBase {

    @Test
    public void testExercise19()
    {
        app.addProductsToCart(4);

        app.checkOut();

        app.deleteAllProductsInCart();

        Assert.assertTrue("Expect: There are no items in your cart.", app.isCartEmpty());
    }
}
