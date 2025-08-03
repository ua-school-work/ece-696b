package edu.arizona.josesosa.creational.factory.store.impl;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.cart.CartLineItem;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.store.Store;

public class Amazon extends Store {
    /**
     * Constructor
     */
    public Amazon() {
        super(new AmazonFactory());
    }

    @Override
    protected void hookProcess(Cart order) throws Exception {
        System.out.println(Amazon.class.getSimpleName() + " is happy for your order");
        for (CartLineItem line : order.getOrderList()) {
            Product product = line.getProduct();
            System.out.println("+ " + product.getName() + " " + line.getQuantity() + "x " + product.getPrice());

        }
        System.out.println("Total: " + order.getTotal());
    }
}
