package edu.arizona.josesosa.creational.factory.product.impl;

import edu.arizona.josesosa.creational.factory.product.Product;

import java.math.BigDecimal;
import java.util.Map;

public class EbayProduct extends Product {

    private static final Map<String, Double> productRank;

    static {
        // hack for the illustration
        productRank = Map.of("Soap", 2.4, "Lego", 5.0, "Tabaco", 4.1, "Book", 2.1);
    }

    private double rank = 0.0;

    public EbayProduct(String name, String description, BigDecimal price, double rank) {
        super(name, description, price);
        this.rank = rank;
    }

    public EbayProduct(String name) {
        super(name);
        if (productRank.containsKey(name)) {
            rank = productRank.get(name);
        }
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public static EbayProduct make(String name) {
        return new EbayProduct(name);
    }

    public EbayProduct init(String description, BigDecimal price, double rank) {
        super.init(description, price);
        this.rank = rank;
        return this;
    }
}
