package edu.arizona.josesosa.structural.bridge.model;

import edu.arizona.josesosa.structural.bridge.model.Cart;
import edu.arizona.josesosa.structural.bridge.model.OrderTracking;

public class DistributorOrder {
    private final String orderId;
    private final String customerOrderHandle;
    private final Cart cart;
    private final String toAddress;
    private final String fromAddress;
    private boolean isWrapped;
    private boolean isPickup;
    private String branchLocation;
    private boolean isPaid;
    private boolean isRouted;
    private boolean isDelivered;
    private boolean isCancelled;

    public DistributorOrder(String orderId, String customerOrderHandle, Cart cart, String toAddress, String fromAddress) {
        this.orderId = orderId;
        this.customerOrderHandle = customerOrderHandle;
        this.cart = cart;
        this.toAddress = toAddress;
        this.fromAddress = fromAddress;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerOrderHandle() { return customerOrderHandle; }
    public Cart getCart() { return cart; }
    public String getToAddress() { return toAddress; }
    public String getFromAddress() { return fromAddress; }
    public boolean isWrapped() { return isWrapped; }
    public boolean isPickup() { return isPickup; }

    public boolean canBeModified() {
        return !isPaid && !isRouted && !isCancelled;
    }

    public boolean addWrapping() {
        if (!canBeModified()) return false;
        isWrapped = true;
        return true;
    }

    public boolean requestPickup() {
        if (!canBeModified()) return false;
        isPickup = true;
        return true;
    }

    public boolean setBranchLocation(String location) {
        if (!canBeModified()) return false;
        this.branchLocation = location;
        return true;
    }

    public boolean markAsPaid() {
        if (!canBeModified()) return false;
        isPaid = true;
        return true;
    }

    public boolean markAsRouted() {
        if (isPaid && !isRouted && !isCancelled) {
            isRouted = true;
            return true;
        }
        return false;
    }

    public boolean markAsCancelled() {
        if (!isRouted && !isDelivered && !isCancelled) {
            isCancelled = true;
            return true;
        }
        return false;
    }

    public boolean markAsDelivered() {
        if (isPaid && isRouted && !isDelivered && !isCancelled) {
            isDelivered = true;
            return true;
        }
        return false;
    }

    public OrderTracking.Status getStatus() {
        if (isCancelled) return OrderTracking.Status.CANCELLED;
        if (isDelivered) return OrderTracking.Status.DELIVERED;
        if (isRouted) return OrderTracking.Status.SHIPPED;
        return OrderTracking.Status.PENDING;
    }
    
    public String getAdditionalTrackingInfo() {
        StringBuilder additionalInfo = new StringBuilder();
        if (isWrapped) {
            additionalInfo.append("Wrapped package. ");
        }
        if (isPickup) {
            additionalInfo.append("Pickup from customer address. ");
        }
        if (branchLocation != null) {
            additionalInfo.append("Drop-off at branch: ").append(branchLocation).append(". ");
        }
        return additionalInfo.toString().trim();
    }
}
