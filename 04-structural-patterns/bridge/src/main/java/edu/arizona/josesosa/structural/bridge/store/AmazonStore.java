package edu.arizona.josesosa.structural.bridge.store;

import edu.arizona.josesosa.structural.bridge.distributor.Distributor;

/**
 * Amazon implementation of the AbstractStore abstract class
 * Amazon always requests pickup service from the distributor
 */
public class AmazonStore extends AbstractStore {
    
    public AmazonStore(String address, Distributor distributor) {
        super("Amazon", address, distributor);
    }
    
    @Override
    protected void applyDeliveryStrategy(String distributorOrderId) {
        // Amazon always requests pickup service
        distributor.requestPickup(distributorOrderId);
    }
}