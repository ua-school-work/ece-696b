package edu.arizona.josesosa.structural.bridge.distributor;

import edu.arizona.josesosa.structural.bridge.model.Cart;
import edu.arizona.josesosa.structural.bridge.model.DistributorOrder;
import edu.arizona.josesosa.structural.bridge.model.OrderTracking;
import edu.arizona.josesosa.structural.bridge.repository.OrderRepository;

import java.util.Optional;

/**
 * Abstract base class for distributors that implements common functionality
 */
public abstract class AbstractDistributor implements Distributor {
    protected final String name;
    protected final OrderRepository orderRepository = new OrderRepository();

    public AbstractDistributor(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String fileTemporaryOrder(String customerOrderHandle, Cart cart, String toAddress, String fromAddress) {
        String orderId = generateOrderId();
        DistributorOrder order = new DistributorOrder(orderId, customerOrderHandle, cart, toAddress, fromAddress);
        orderRepository.save(order);
        return orderId;
    }

    @Override
    public boolean addWrapping(String orderId) {
        return orderRepository.findById(orderId)
                .map(DistributorOrder::addWrapping)
                .orElse(false);
    }

    @Override
    public boolean requestPickup(String orderId) {
        return orderRepository.findById(orderId)
                .map(DistributorOrder::requestPickup)
                .orElse(false);
    }

    @Override
    public boolean dropOffAtBranch(String orderId, String branchLocation) {
        return orderRepository.findById(orderId)
                .map(order -> order.setBranchLocation(branchLocation))
                .orElse(false);
    }

    @Override
    public boolean payForOrder(String orderId, double amount) {
        Optional<DistributorOrder> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return false;
        }

        DistributorOrder order = orderOpt.get();
        if (!order.canBeModified()) {
            return false;
        }

        double expectedAmount = getCostQuote(orderId);
        if (Math.abs(amount - expectedAmount) > 0.01) {
            return false;
        }

        return order.markAsPaid();
    }

    @Override
    public boolean routeOrder(String orderId) {
        return orderRepository.findById(orderId)
                .map(DistributorOrder::markAsRouted)
                .orElse(false);
    }

    @Override
    public String lookupOrder(String customerOrderHandle) {
        return orderRepository.findOrderIdByCustomerHandle(customerOrderHandle).orElse(null);
    }

    @Override
    public boolean cancelOrder(String orderId) {
        return orderRepository.findById(orderId)
                .map(DistributorOrder::markAsCancelled)
                .orElse(false);
    }

    @Override
    public boolean confirmDelivery(String orderId) {
        return orderRepository.findById(orderId)
                .map(DistributorOrder::markAsDelivered)
                .orElse(false);
    }

    @Override
    public OrderTracking getTracking(String orderId) {
        return orderRepository.findById(orderId)
                .map(this::createOrderTrackingFromOrder)
                .orElse(null);
    }

    private OrderTracking createOrderTrackingFromOrder(DistributorOrder order) {
        OrderTracking tracking = new OrderTracking(order.getOrderId(), order.getStatus(), order.getFromAddress(), order.getToAddress());
        tracking.setAdditionalInfo(order.getAdditionalTrackingInfo());
        return tracking;
    }

    @Override
    public double getCostQuote(String orderId) {
        return orderRepository.findById(orderId)
                .map(this::calculateOrderCost)
                .orElse(0.0);
    }

    private double calculateOrderCost(DistributorOrder order) {
        double cost = calculateBaseDeliveryCost(order);

        if (order.isWrapped()) {
            cost += calculateWrappingCost(order);
        }

        if (order.isPickup()) {
            cost += calculatePickupCost(order);
        }

        return cost;
    }

    protected abstract String generateOrderId();

    protected abstract double calculateBaseDeliveryCost(DistributorOrder order);

    protected abstract double calculateWrappingCost(DistributorOrder order);

    protected abstract double calculatePickupCost(DistributorOrder order);
}