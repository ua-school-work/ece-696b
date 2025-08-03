package edu.arizona.josesosa.creational.factory.product.impl;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonProductTest {

    @Test
    public void testAmazonProductApplies10PercentDiscount() {
        // Create an AmazonProduct with a price of 100
        AmazonProduct product = new AmazonProduct("Test Product", "Test Description", new BigDecimal("100"));
        
        // The getPrice method should return the price with a 10% discount (90)
        assertEquals(new BigDecimal("90.0"), product.getPrice());
        
        // Test with setPrice
        product.setPrice(new BigDecimal("200"));
        assertEquals(new BigDecimal("180.0"), product.getPrice());
        
        // Test with init
        product = new AmazonProduct("Test Product");
        product.init("Test Description", new BigDecimal("300"));
        assertEquals(new BigDecimal("270.0"), product.getPrice());
    }
}