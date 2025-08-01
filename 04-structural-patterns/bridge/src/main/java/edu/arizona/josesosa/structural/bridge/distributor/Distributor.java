package edu.arizona.josesosa.structural.bridge.distributor;

import edu.arizona.josesosa.structural.bridge.model.Cart;
import edu.arizona.josesosa.structural.bridge.model.OrderTracking;

/**
 * Distributor interface - the Implementor in the Bridge pattern
 * Defines the interface for all concrete distributors
 */
public interface Distributor {
    /**
     * Files a temporary order and returns the order code ID
     * @param customerOrderHandle Customer's order handle/reference
     * @param cart The cart containing items to be shipped
     * @param toAddress The delivery address
     * @param fromAddress The pickup address
     * @return The order code ID
     */
    String fileTemporaryOrder(String customerOrderHandle, Cart cart, String toAddress, String fromAddress);
    
    /**
     * Adds wrapping service to the order
     * @param orderId The order ID
     * @return true if wrapping was added successfully
     */
    boolean addWrapping(String orderId);
    
    /**
     * Requests pickup service at the customer location
     * @param orderId The order ID
     * @return true if pickup service was added successfully
     */
    boolean requestPickup(String orderId);
    
    /**
     * Indicates that the order will be dropped off at a branch
     * @param orderId The order ID
     * @param branchLocation The branch location
     * @return true if drop-off was registered successfully
     */
    boolean dropOffAtBranch(String orderId, String branchLocation);
    
    /**
     * Gets the cost quote for the order
     * @param orderId The order ID
     * @return The cost of delivery
     */
    double getCostQuote(String orderId);
    
    /**
     * Pays for the order and makes it an actual distribution order
     * @param orderId The order ID
     * @param amount The amount to pay
     * @return true if payment was successful
     */
    boolean payForOrder(String orderId, double amount);
    
    /**
     * Marks the order as routed (internal call)
     * @param orderId The order ID
     * @return true if the order was routed successfully
     */
    boolean routeOrder(String orderId);
    
    /**
     * Looks up an order by customer handle
     * @param customerOrderHandle The customer's order handle/reference
     * @return The order ID or null if not found
     */
    String lookupOrder(String customerOrderHandle);
    
    /**
     * Attempts to cancel the order if it has not been routed yet
     * @param orderId The order ID
     * @return true if cancellation was successful
     */
    boolean cancelOrder(String orderId);
    
    /**
     * Confirms delivery of the order (internal call)
     * @param orderId The order ID
     * @return true if delivery confirmation was successful
     */
    boolean confirmDelivery(String orderId);
    
    /**
     * Gets tracking information for the order
     * @param orderId The order ID
     * @return The tracking information
     */
    OrderTracking getTracking(String orderId);
    
    /**
     * Gets the name of the distributor
     * @return The distributor name
     */
    String getName();
}