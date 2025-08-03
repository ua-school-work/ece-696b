package edu.arizona.josesosa.creational.factory;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.product.impl.AmazonProduct;
import edu.arizona.josesosa.creational.factory.store.Store;
import edu.arizona.josesosa.creational.factory.store.impl.Amazon;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonUserTest extends UserTest{
    protected Store makeStore() {
        return new Amazon();
    }
    
    @Override
    protected Product makeProduct(String name) {
        return new AmazonProduct(name);
    }

    @Test
    public void testTotal() throws Exception {
        Cart cart = makeAnOrder();
        assertEquals(new BigDecimal("171.0"), cart.getTotal());
    }

}
