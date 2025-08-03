package edu.arizona.josesosa.creational.factory.distributor.impl;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;

import java.math.BigDecimal;
import java.net.URL;

public class UPS extends Distributor {
    /**
     * @return cost of the shipment
     */
    @Override
    public BigDecimal getCharge() {
        return new BigDecimal(160);
    }

    /**
     * @return user rank
     */
    @Override
    public double getRank() {
        return 4.8;
    }

    /**
     * @return tracking URL
     * @throws Exception
     */
    @Override
    public URL getTrackingLink() throws Exception {
        return new URL("http://www.ups.com/tracking");
    }

    /**
     * Shipping a cart
     *
     * @param order
     * @throws Exception
     */
    @Override
    public void ship(Cart order) throws Exception {
        shipTracing("UPS", order);
        System.out.println("# Pickup at UPS vendor");
        System.out.println("# Send to general UPS distribution center");
        System.out.println("# Send to UPS local distribution center");
        System.out.println("# Distribute locally");
        System.out.println("# Distribute locally");
        System.out.println("# Send to Customer");
    }
}
