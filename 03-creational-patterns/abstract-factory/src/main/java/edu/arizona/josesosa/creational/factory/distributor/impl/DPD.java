package edu.arizona.josesosa.creational.factory.distributor.impl;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.cart.Cart;

import java.math.BigDecimal;
import java.net.URL;

public class DPD extends Distributor {

    @Override
    public BigDecimal getCharge() {
        return new BigDecimal(130);
    }

    @Override
    public double getRank() {
        return 4.6;
    }

    @Override
    public URL getTrackingLink() throws Exception {
        return new URL("http://www.dpd.com/tracking");
    }

    @Override
    public void ship(Cart order) throws Exception {
        shipTracing("DPD", order);
        System.out.println("# Pickup at vendor");
        System.out.println("# Send to Customer");
    }

}
