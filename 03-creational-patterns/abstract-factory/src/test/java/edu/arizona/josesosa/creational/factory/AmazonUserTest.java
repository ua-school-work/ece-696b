package edu.arizona.josesosa.creational.factory;

import edu.arizona.josesosa.creational.abstractfactory.AbstractFactory;
import edu.arizona.josesosa.creational.abstractfactory.AmazonTestFactory;
import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.store.Store;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonUserTest extends UserTest {
    @Override
    protected AbstractFactory makeFactory() {
        return new AmazonTestFactory();
    }

    @Test
    public void testTotal() throws Exception {
        Cart cart = makeAnOrder();
        assertEquals(new BigDecimal("171.0"), cart.getTotal());
    }

}
