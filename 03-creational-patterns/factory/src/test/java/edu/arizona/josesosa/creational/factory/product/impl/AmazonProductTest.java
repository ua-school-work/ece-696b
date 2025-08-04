package edu.arizona.josesosa.creational.factory.product.impl;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonProductTest {

    private static final String DUMMY_NAME = "Test Product";
    private static final String DUMMY_DESCRIPTION = "Test Description";

    @Test
    void priceDiscountedWhenSetThroughConstructor() {
        AmazonProduct product = createProductWithPriceViaConstructor("100");
        assertPriceIsDiscounted(product, "90.0");
    }

    @Test
    void priceDiscountedWhenSetThroughSetter() {
        AmazonProduct product = createProductWithPriceViaConstructor("100");
        product.setPrice(new BigDecimal("200"));
        assertPriceIsDiscounted(product, "180.0");
    }

    @Test
    void priceDiscountedWhenSetThroughInit() {
        AmazonProduct product = createProductAndInitWithPrice("300");
        assertPriceIsDiscounted(product, "270.0");
    }

    private AmazonProduct createProductWithPriceViaConstructor(String price) {
        return new AmazonProduct(DUMMY_NAME, DUMMY_DESCRIPTION, new BigDecimal(price));
    }

    private AmazonProduct createProductAndInitWithPrice(String price) {
        AmazonProduct product = new AmazonProduct(DUMMY_NAME);
        product.init(DUMMY_DESCRIPTION, new BigDecimal(price));
        return product;
    }

    private void assertPriceIsDiscounted(AmazonProduct product, String expectedPrice) {
        assertEquals(new BigDecimal(expectedPrice), product.getPrice());
    }
}