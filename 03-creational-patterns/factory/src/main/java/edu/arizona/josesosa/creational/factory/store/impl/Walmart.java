package edu.arizona.josesosa.creational.factory.store.impl;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.cart.CartLineItem;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.store.Store;

public class Walmart extends Store {

    public Walmart() {
        super(new WalmartFactory());
    }

    @Override
    protected void hookProcess(Cart order) throws Exception {
        printConfirmationMessage();
        printOrderItems(order);
        printTotal(order);
    }

    private void printConfirmationMessage() {
        System.out.println(Walmart.class.getSimpleName() + " is happy for your order");
    }

    private void printOrderItems(Cart order) {
        for (CartLineItem line : order.getOrderList()) {
            Product product = line.getProduct();
            System.out.println("+ " + product.getName() + " " + line.getQuantity() + "x " + product.getPrice());

        }
    }

    private void printTotal(Cart order) {
        System.out.println("Total: " + order.getTotal());
    }

}