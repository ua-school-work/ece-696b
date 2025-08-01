package edu.arizona.josesosa.structural.bridge.distributor;

import edu.arizona.josesosa.structural.bridge.model.DistributorOrder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * UPS implementation of the Distributor interface
 * UPS is cheaper for wrapping but more expensive for pickup
 */
public class UPSDistributor extends AbstractDistributor {
    private static final double BASE_DELIVERY_COST = 10.0;
    private static final double WRAPPING_COST = 2.0; // Cheaper wrapping
    private static final double PICKUP_COST = 8.0; // More expensive pickup
    
    private final AtomicInteger orderCounter = new AtomicInteger(1000);
    
    public UPSDistributor() {
        super("UPS");
    }
    
    @Override
    protected String generateOrderId() {
        return "UPS-" + orderCounter.getAndIncrement();
    }
    
    @Override
    protected double calculateBaseDeliveryCost(DistributorOrder order) {
        // In a real implementation, this would calculate based on distance, weight, etc.
        // For this demo, we'll use a simple fixed cost
        return BASE_DELIVERY_COST;
    }
    
    @Override
    protected double calculateWrappingCost(DistributorOrder order) {
        // UPS has less expensive wrapping
        return WRAPPING_COST;
    }
    
    @Override
    protected double calculatePickupCost(DistributorOrder order) {
        // UPS has a more expensive pickup
        return PICKUP_COST;
    }
}