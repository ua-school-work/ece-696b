package edu.arizona.josesosa.creational.factory.product;

import java.math.BigDecimal;

public class Product {

    private String name;
    private String description;
    private BigDecimal price;

    public Product(String name, String description, BigDecimal price) {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product() {
    }

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static Product make(String name) {
        return new Product(name);
    }

    public Product init(String description, BigDecimal price) {
        this.description = description;
        this.price = price;
        return this;
    }

}
