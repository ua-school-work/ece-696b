package edu.arizona.josesosa.creational.factory.store.impl;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.cart.CartLineItem;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.distributor.impl.DPD;
import edu.arizona.josesosa.creational.factory.distributor.impl.USPS;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.store.Store;

import java.util.Arrays;
import java.util.List;

public class Walmart extends Store {

    private List<Distributor> distributorList = null;

    public Walmart() {
        Distributor[] distributors = {new USPS(), new DPD()};
        distributorList = Arrays.asList(distributors);
    }

    public Walmart(List<Distributor> distributorList) {
        this.distributorList = distributorList;
    }

    @Override
    public List<Distributor> getDistributorList() {
        return distributorList;
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
