package edu.arizona.josesosa.structural.bridge;

import edu.arizona.josesosa.structural.bridge.distributor.Distributor;
import edu.arizona.josesosa.structural.bridge.distributor.FedExDistributor;
import edu.arizona.josesosa.structural.bridge.distributor.UPSDistributor;
import edu.arizona.josesosa.structural.bridge.model.Cart;
import edu.arizona.josesosa.structural.bridge.model.OrderTracking;
import edu.arizona.josesosa.structural.bridge.model.Product;
import edu.arizona.josesosa.structural.bridge.store.AbstractStore;
import edu.arizona.josesosa.structural.bridge.store.AmazonStore;
import edu.arizona.josesosa.structural.bridge.store.WalmartStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BridgePatternTest {
    private Distributor ups;
    private Distributor fedEx;
    private AbstractStore amazonWithUPS;
    private AbstractStore amazonWithFedEx;
    private AbstractStore walmartWithUPS;
    private AbstractStore walmartWithFedEx;
    private Cart cart;
    
    @BeforeEach
    void setUp() {
        // Create distributors
        ups = new UPSDistributor();
        fedEx = new FedExDistributor();
        
        // Create stores with different distributors
        amazonWithUPS = new AmazonStore("123 Amazon Way, Seattle, WA", ups);
        amazonWithFedEx = new AmazonStore("123 Amazon Way, Seattle, WA", fedEx);
        
        walmartWithUPS = new WalmartStore("456 Walmart Blvd, Bentonville, AR",
                "UPS Branch #42, Bentonville, AR", ups);
        walmartWithFedEx = new WalmartStore("456 Walmart Blvd, Bentonville, AR",
                "FedEx Branch #17, Bentonville, AR", fedEx);
        
        // Create a sample cart
        cart = new Cart();
        cart.addItem(new Product("P1", "Laptop", 999.99), 1);
        cart.addItem(new Product("P2", "Mouse", 24.99), 2);
        cart.addItem(new Product("P3", "Keyboard", 49.99), 1);
    }
    
    @Test
    void testSuccessfulOrder() {
        // Test Amazon with UPS
        String customerOrderId = amazonWithUPS.previewOrder(cart, "789 Customer St, Phoenix, AZ");
        System.out.println("Amazon with UPS - Order Preview:");
        System.out.println(amazonWithUPS.getOrderDetails(customerOrderId));
        
        // Process payment
        boolean paymentSuccess = amazonWithUPS.processOrderPayment(customerOrderId);
        assertTrue(paymentSuccess, "Payment should be successful");
        
        // Simulate order routing (internal process)
        String distributorOrderId = ups.lookupOrder(customerOrderId);
        ups.routeOrder(distributorOrderId);
        
        // Check tracking
        OrderTracking tracking = amazonWithUPS.getOrderTracking(customerOrderId);
        assertEquals(OrderTracking.Status.SHIPPED, tracking.getStatus(), "Order should be shipped");
        System.out.println("Amazon with UPS - Tracking:");
        System.out.println(tracking);
        
        // Simulate delivery confirmation (internal process)
        ups.confirmDelivery(distributorOrderId);
        
        // Check tracking again
        tracking = amazonWithUPS.getOrderTracking(customerOrderId);
        assertEquals(OrderTracking.Status.DELIVERED, tracking.getStatus(), "Order should be delivered");
        System.out.println("Amazon with UPS - Updated Tracking:");
        System.out.println(tracking);
    }
    
    @Test
    void testSuccessfulOrderWithDifferentStoreAndDistributor() {
        // Test Walmart with FedEx
        String customerOrderId = walmartWithFedEx.previewOrder(cart, "789 Customer St, Phoenix, AZ");
        System.out.println("Walmart with FedEx - Order Preview:");
        System.out.println(walmartWithFedEx.getOrderDetails(customerOrderId));
        
        // Process payment
        boolean paymentSuccess = walmartWithFedEx.processOrderPayment(customerOrderId);
        assertTrue(paymentSuccess, "Payment should be successful");
        
        // Simulate order routing (internal process)
        String distributorOrderId = fedEx.lookupOrder(customerOrderId);
        fedEx.routeOrder(distributorOrderId);
        
        // Check tracking
        OrderTracking tracking = walmartWithFedEx.getOrderTracking(customerOrderId);
        assertEquals(OrderTracking.Status.SHIPPED, tracking.getStatus(), "Order should be shipped");
        System.out.println("Walmart with FedEx - Tracking:");
        System.out.println(tracking);
    }
    
    @Test
    void testSuccessfulCancellation() {
        // Test Amazon with FedEx
        String customerOrderId = amazonWithFedEx.previewOrder(cart, "789 Customer St, Phoenix, AZ");
        
        // Cancel order before payment
        boolean cancellationSuccess = amazonWithFedEx.cancelOrder(customerOrderId);
        assertTrue(cancellationSuccess, "Cancellation should be successful");
        
        // Check tracking
        OrderTracking tracking = amazonWithFedEx.getOrderTracking(customerOrderId);
        assertEquals(OrderTracking.Status.CANCELLED, tracking.getStatus(), "Order should be cancelled");
        System.out.println("Amazon with FedEx - Cancelled Order Tracking:");
        System.out.println(tracking);
    }
    
    @Test
    void testDeclinedCancellation() {
        // Test Walmart with UPS
        String customerOrderId = walmartWithUPS.previewOrder(cart, "789 Customer St, Phoenix, AZ");
        
        // Process payment
        walmartWithUPS.processOrderPayment(customerOrderId);
        
        // Simulate order routing (internal process)
        String distributorOrderId = ups.lookupOrder(customerOrderId);
        ups.routeOrder(distributorOrderId);
        
        // Try to cancel after routing (should be declined)
        boolean cancellationSuccess = walmartWithUPS.cancelOrder(customerOrderId);
        assertFalse(cancellationSuccess, "Cancellation should be declined after routing");
        
        // Check tracking
        OrderTracking tracking = walmartWithUPS.getOrderTracking(customerOrderId);
        assertEquals(OrderTracking.Status.SHIPPED, tracking.getStatus(), "Order should still be shipped");
        System.out.println("Walmart with UPS - Declined Cancellation Tracking:");
        System.out.println(tracking);
    }
    
    @Test
    void testPricingDifferences() {
        // Test Amazon with both distributors (uses pickup)
        String amazonUpsOrderId = amazonWithUPS.previewOrder(cart, "789 Customer St, Phoenix, AZ");
        String amazonFedExOrderId = amazonWithFedEx.previewOrder(cart, "789 Customer St, Phoenix, AZ");
        
        System.out.println("Amazon with UPS - Order Preview (uses pickup):");
        System.out.println(amazonWithUPS.getOrderDetails(amazonUpsOrderId));
        
        System.out.println("Amazon with FedEx - Order Preview (uses pickup):");
        System.out.println(amazonWithFedEx.getOrderDetails(amazonFedExOrderId));
        
        // Test Walmart with both distributors (uses wrapping and branch drop-off)
        String walmartUpsOrderId = walmartWithUPS.previewOrder(cart, "789 Customer St, Phoenix, AZ");
        String walmartFedExOrderId = walmartWithFedEx.previewOrder(cart, "789 Customer St, Phoenix, AZ");
        
        System.out.println("Walmart with UPS - Order Preview (uses wrapping and branch drop-off):");
        System.out.println(walmartWithUPS.getOrderDetails(walmartUpsOrderId));
        
        System.out.println("Walmart with FedEx - Order Preview (uses wrapping and branch drop-off):");
        System.out.println(walmartWithFedEx.getOrderDetails(walmartFedExOrderId));
    }
}