package edu.arizona.josesosa.creational.factory.distributor.impl;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.cart.Cart;

import java.math.BigDecimal;
import java.net.URL;

public class USPS extends Distributor {

    @Override
    public BigDecimal getCharge() {
        return new BigDecimal(150);
    }

    @Override
    public double getRank() {
        return 2.5;
    }

    @Override
    public URL getTrackingLink() throws Exception {
        return new URL("http://www.usps.com/tracking");
    }

    @Override
    public void ship(Cart order) throws Exception {
        shipTracing("usps", order);
        System.out.println("# Send to Chicago");
        System.out.println("# Distribute locally");
        System.out.println("# Distribute locally");
        System.out.println("# Distribute locally");
        System.out.println("# Send to Customer");
    }

}
