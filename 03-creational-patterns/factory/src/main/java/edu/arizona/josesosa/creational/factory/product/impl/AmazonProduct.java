package edu.arizona.josesosa.creational.factory.product.impl;

import edu.arizona.josesosa.creational.factory.product.Product;

import java.math.BigDecimal;

public class AmazonProduct extends Product {

    private BigDecimal originalPrice;

    public AmazonProduct(String name, String description, BigDecimal price) {
        super(name, description, price);
        this.originalPrice = price;
    }

    public AmazonProduct(String name) {
        super(name);
    }

    @Override
    public void setPrice(BigDecimal price) {
        super.setPrice(applyDiscount(price));
        this.originalPrice = price;
    }

    private BigDecimal applyDiscount(BigDecimal originalPrice) {
        return originalPrice.multiply(new BigDecimal("0.9"));
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public static AmazonProduct make(String name) {
        return new AmazonProduct(name);
    }

    @Override
    public Product init(String description, BigDecimal price) {
        super.init(description, price);
        this.originalPrice = price;
        return this;
    }
}