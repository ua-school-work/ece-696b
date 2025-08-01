package edu.arizona.josesosa.structural.bridge;

import edu.arizona.josesosa.structural.bridge.distributor.Distributor;
import edu.arizona.josesosa.structural.bridge.distributor.FedExDistributor;
import edu.arizona.josesosa.structural.bridge.distributor.UPSDistributor;
import edu.arizona.josesosa.structural.bridge.model.Cart;
import edu.arizona.josesosa.structural.bridge.model.Product;
import edu.arizona.josesosa.structural.bridge.store.AbstractStore;
import edu.arizona.josesosa.structural.bridge.store.AmazonStore;
import edu.arizona.josesosa.structural.bridge.store.WalmartStore;

public class BridgePatternDemo {

    public static void runDemo(String[] args) {
        Distributor ups = new UPSDistributor();
        Distributor fedEx = new FedExDistributor();

        AbstractStore amazonWithUPS = new AmazonStore("123 Amazon Way, Seattle, WA", ups);
        AbstractStore amazonWithFedEx = new AmazonStore("123 Amazon Way, Seattle, WA", fedEx);
        AbstractStore walmartWithUPS = new WalmartStore("456 Walmart Blvd, Bentonville, AR", "UPS Branch #42", ups);
        AbstractStore walmartWithFedEx = new WalmartStore("456 Walmart Blvd, Bentonville, AR", "FedEx Branch #17", fedEx);

        Cart cart = createSampleCart();

        printIntroduction();

        demonstrateSuccessfulOrder(amazonWithUPS, cart);
        demonstrateSuccessfulOrder(walmartWithFedEx, cart);
        demonstrateSuccessfulCancellation(amazonWithFedEx, cart);
        demonstrateDeclinedCancellation(walmartWithUPS, cart);
        demonstratePricingDifferences(amazonWithUPS, amazonWithFedEx, walmartWithUPS, walmartWithFedEx, cart);
    }

    private static Cart createSampleCart() {
        Cart cart = new Cart();
        cart.addItem(new Product("P1", "Laptop", 999.99), 1);
        cart.addItem(new Product("P2", "Mouse", 24.99), 2);
        cart.addItem(new Product("P3", "Keyboard", 49.99), 1);
        return cart;
    }

    private static void printIntroduction() {
        System.out.println("""
        =================================
        BRIDGE PATTERN DEMONSTRATION
        =================================
        This demo shows how different Stores can work with different Distributors.
        - Amazon always uses a pickup service.
        - Walmart always delivers to a branch and uses a wrapping service.
        - UPS has cheaper wrapping but more expensive pickup.
        - FedEx has more expensive wrapping but cheaper pickup.
        ---------------------------------
        """);
    }

    private static void printTestHeader(String title) {
        System.out.printf("""

        //==================================================
        // %s
        //==================================================
        """, title.toUpperCase());
    }

    private static void demonstrateSuccessfulOrder(AbstractStore abstractStore, Cart cart) {
        String scenarioTitle = String.format("SUCCESSFUL ORDER (%s with %s)", abstractStore.getName(), abstractStore.getDistributor().getName());
        printTestHeader(scenarioTitle);

        String customerOrderId = abstractStore.previewOrder(cart, "789 Customer St, Phoenix, AZ");
        System.out.println("--- Order Preview ---");
        System.out.println(abstractStore.getOrderDetails(customerOrderId));

        System.out.println("\n--- Processing Payment ---");
        boolean isPaid = abstractStore.processOrderPayment(customerOrderId);
        System.out.printf("Payment successful: %s%n", isPaid);
        System.out.println("\n--- Initial Tracking ---");
        System.out.println(abstractStore.getOrderTracking(customerOrderId));

        // For demo purposes, we manually confirm delivery to see the status change.
        System.out.println("\n--- Simulating Delivery ---");
        abstractStore.getOrder(customerOrderId).ifPresent(order -> {
            abstractStore.getDistributor().confirmDelivery(order.getDistributorOrderId());
            System.out.println("Delivery confirmed by distributor.");
        });

        System.out.println("\n--- Final Tracking ---");
        System.out.println(abstractStore.getOrderTracking(customerOrderId));
    }

    private static void demonstrateSuccessfulCancellation(AbstractStore abstractStore, Cart cart) {
        String scenarioTitle = String.format("SUCCESSFUL CANCELLATION (%s with %s)", abstractStore.getName(), abstractStore.getDistributor().getName());
        printTestHeader(scenarioTitle);

        String customerOrderId = abstractStore.previewOrder(cart, "789 Customer St, Phoenix, AZ");
        System.out.println("--- Order Preview ---");
        System.out.println(abstractStore.getOrderDetails(customerOrderId));

        System.out.println("\n--- Attempting Cancellation ---");
        boolean isCancelled = abstractStore.cancelOrder(customerOrderId);
        System.out.printf("Cancellation successful: %s%n", isCancelled);

        System.out.println("\n--- Final Tracking ---");
        System.out.println(abstractStore.getOrderTracking(customerOrderId));
    }

    private static void demonstrateDeclinedCancellation(AbstractStore abstractStore, Cart cart) {
        String scenarioTitle = String.format("DECLINED CANCELLATION (%s with %s)", abstractStore.getName(), abstractStore.getDistributor().getName());
        printTestHeader(scenarioTitle);

        String customerOrderId = abstractStore.previewOrder(cart, "789 Customer St, Phoenix, AZ");
        System.out.println("--- Order Preview ---");
        System.out.println(abstractStore.getOrderDetails(customerOrderId));

        System.out.println("\n--- Processing Payment and Routing ---");
        abstractStore.processOrderPayment(customerOrderId);
        System.out.println("Payment processed, order has been routed.");

        System.out.println("\n--- Attempting Cancellation (after routing) ---");
        boolean isCancelled = abstractStore.cancelOrder(customerOrderId);
        System.out.printf("Cancellation successful: %s (Expected: false)%n", isCancelled);

        System.out.println("\n--- Final Tracking ---");
        System.out.println(abstractStore.getOrderTracking(customerOrderId));
    }

    private static void demonstratePricingDifferences(AbstractStore amazonWithUPS, AbstractStore amazonWithFedEx, AbstractStore walmartWithUPS, AbstractStore walmartWithFedEx, Cart cart) {
        printTestHeader("PRICING DIFFERENCES");

        System.out.println("--- Amazon Order Pricing ---");
        String amazonUpsId = amazonWithUPS.previewOrder(cart, "111 Price St, USA");
        System.out.println("With UPS:");
        System.out.println(amazonWithUPS.getOrderDetails(amazonUpsId));

        String amazonFedExId = amazonWithFedEx.previewOrder(cart, "111 Price St, USA");
        System.out.println("\nWith FedEx:");
        System.out.println(amazonWithFedEx.getOrderDetails(amazonFedExId));

        System.out.println("\n--- Walmart Order Pricing ---");
        String walmartUpsId = walmartWithUPS.previewOrder(cart, "222 Price St, USA");
        System.out.println("With UPS:");
        System.out.println(walmartWithUPS.getOrderDetails(walmartUpsId));

        String walmartFedExId = walmartWithFedEx.previewOrder(cart, "222 Price St, USA");
        System.out.println("\nWith FedEx:");
        System.out.println(walmartWithFedEx.getOrderDetails(walmartFedExId));
    }
}