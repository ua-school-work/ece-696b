package edu.arizona.josesosa.structural.bridge.distributor;

import edu.arizona.josesosa.structural.bridge.model.DistributorOrder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * FedEx implementation of the Distributor interface
 * FedEx is more expensive for wrapping but cheaper for pickup
 */
public class FedExDistributor extends AbstractDistributor {
    private static final double BASE_DELIVERY_COST = 12.0;
    private static final double WRAPPING_COST = 5.0; // More expensive wrapping
    private static final double PICKUP_COST = 4.0; // Cheaper pickup
    
    private final AtomicInteger orderCounter = new AtomicInteger(5000);
    
    public FedExDistributor() {
        super("FedEx");
    }
    
    @Override
    protected String generateOrderId() {
        return "FEDEX-" + orderCounter.getAndIncrement();
    }
    
    @Override
    protected double calculateBaseDeliveryCost(DistributorOrder order) {
        // In a real implementation, this would calculate based on distance, weight, etc.
        // For this demo, we'll use a simple fixed cost
        return BASE_DELIVERY_COST;
    }
    
    @Override
    protected double calculateWrappingCost(DistributorOrder order) {
        // FedEx has more expensive wrapping
        return WRAPPING_COST;
    }
    
    @Override
    protected double calculatePickupCost(DistributorOrder order) {
        // FedEx has a less expensive pickup
        return PICKUP_COST;
    }
}