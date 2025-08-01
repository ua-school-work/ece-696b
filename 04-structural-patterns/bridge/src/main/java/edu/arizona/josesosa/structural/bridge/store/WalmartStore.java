package edu.arizona.josesosa.structural.bridge.store;

import edu.arizona.josesosa.structural.bridge.distributor.Distributor;

/**
 * Walmart implementation of the AbstractStore abstract class
 * Walmart always delivers to branch and requests wrapping service
 */
public class WalmartStore extends AbstractStore {
    private final String branchLocation;
    
    public WalmartStore(String address, String branchLocation, Distributor distributor) {
        super("Walmart", address, distributor);
        this.branchLocation = branchLocation;
    }
    
    @Override
    protected void applyDeliveryStrategy(String distributorOrderId) {
        // Walmart always delivers to branch
        distributor.dropOffAtBranch(distributorOrderId, branchLocation);
        
        // Walmart always requests wrapping service
        distributor.addWrapping(distributorOrderId);
    }
}