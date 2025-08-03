package edu.arizona.josesosa.creational.factory.store.impl;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.cart.CartLineItem;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.distributor.impl.DPD;
import edu.arizona.josesosa.creational.factory.distributor.impl.UPS;
import edu.arizona.josesosa.creational.factory.distributor.impl.USPS;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.store.Store;

import java.util.Arrays;
import java.util.List;

public class Walmart extends Store {

    public Walmart() {
        super(new WalmartFactory());
    }

    @Override
    protected void hookProcess(Cart order) throws Exception {
        System.out.println(Walmart.class.getSimpleName() + " is happy for your order");
        for (CartLineItem line : order.getOrderList()) {
            Product product = line.getProduct();
            System.out.println("+ " + product.getName() + " " + line.getQuantity() + "x " + product.getPrice());

        }
        System.out.println("Total: " + order.getTotal());

    }

}
