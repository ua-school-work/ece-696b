package edu.arizona.josesosa.creational.factory.store.impl;

import edu.arizona.josesosa.creational.abstractfactory.AmazonFactory;
import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.cart.CartLineItem;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.store.Store;

import java.util.List;

public class Amazon extends Store {
    /**
     * Constructor with default factory
     */
    public Amazon() {
        super(new edu.arizona.josesosa.creational.abstractfactory.AmazonFactory().makeStore().getDistributors());
    }
    
    /**
     * Constructor with distributor list
     * 
     * @param distributorList List of distributors for this store
     */
    public Amazon(List<Distributor> distributorList) {
        super(distributorList);
    }

    @Override
    protected void hookProcess(Cart order) throws Exception {
        printConfirmationMessage();
        printOrderItems(order);
        printTotal(order);
    }

    private void printConfirmationMessage() {
        System.out.println(Amazon.class.getSimpleName() + " is happy for your order");
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