package edu.arizona.josesosa.structural.bridge.store;

import edu.arizona.josesosa.structural.bridge.distributor.Distributor;
import edu.arizona.josesosa.structural.bridge.model.Cart;
import edu.arizona.josesosa.structural.bridge.model.OrderTracking;
import edu.arizona.josesosa.structural.bridge.model.StoreOrder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractStore {
    protected final String name;
    protected final String address;
    protected final Distributor distributor;
    protected final Map<String, StoreOrder> orders = new HashMap<>();

    public AbstractStore(String name, String address, Distributor distributor) {
        this.name = name;
        this.address = address;
        this.distributor = distributor;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public Optional<StoreOrder> getOrder(String customerOrderId) {
        return Optional.ofNullable(orders.get(customerOrderId));
    }

    public String previewOrder(Cart cart, String customerAddress) {
        String customerOrderId = generateCustomerOrderId();
        StoreOrder order = new StoreOrder(customerOrderId, cart, customerAddress);

        String distributorOrderId = distributor.fileTemporaryOrder(customerOrderId, cart, customerAddress, this.address);
        order.setDistributorOrderId(distributorOrderId);

        applyDeliveryStrategy(distributorOrderId);

        double deliveryCost = distributor.getCostQuote(distributorOrderId);
        order.setDeliveryCost(deliveryCost);

        orders.put(customerOrderId, order);
        return customerOrderId;
    }

    public String getOrderDetails(String customerOrderId) {
        return getOrder(customerOrderId)
                .map(StoreOrder::toString)
                .orElse("Order not found.");
    }

    public boolean processOrderPayment(String customerOrderId) {
        return getOrder(customerOrderId)
                .map(order -> {
                    if (order.isPaid()) {
                        return false;
                    }
                    boolean paidSuccessfully = distributor.payForOrder(order.getDistributorOrderId(), order.getDeliveryCost());
                    if (paidSuccessfully) {
                        order.markAsPaid();
                        return distributor.routeOrder(order.getDistributorOrderId());
                    }
                    return false;
                })
                .orElse(false);
    }

    public boolean cancelOrder(String customerOrderId) {
        return getOrder(customerOrderId)
                .map(order -> {
                    if (order.isPaid()) {
                        return false; // Do not allow cancellation after payment
                    }
                    return distributor.cancelOrder(order.getDistributorOrderId());
                })
                .orElse(false);
    }

    public OrderTracking getOrderTracking(String customerOrderId) {
        return getOrder(customerOrderId)
                .map(order -> distributor.getTracking(order.getDistributorOrderId()))
                .orElse(null);
    }

    protected String generateCustomerOrderId() {
        return UUID.randomUUID().toString();
    }

    protected abstract void applyDeliveryStrategy(String distributorOrderId);
}