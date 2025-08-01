package edu.arizona.josesosa.structural.bridge.model;

public class StoreOrder {
    private final String customerOrderId;
    private String distributorOrderId;
    private final Cart cart;
    private final String customerAddress;
    private final double goodsTotal;
    private double deliveryCost;
    private boolean isPaid;

    public StoreOrder(String customerOrderId, Cart cart, String customerAddress) {
        this.customerOrderId = customerOrderId;
        this.cart = cart;
        this.customerAddress = customerAddress;
        this.goodsTotal = cart.getTotal();
        this.isPaid = false;
    }

    public String getCustomerOrderId() { return customerOrderId; }
    public String getDistributorOrderId() { return distributorOrderId; }
    public double getDeliveryCost() { return deliveryCost; }
    public boolean isPaid() { return isPaid; }
    public double getTotalCost() { return goodsTotal + deliveryCost; }

    public void setDistributorOrderId(String distributorOrderId) { this.distributorOrderId = distributorOrderId; }
    public void setDeliveryCost(double deliveryCost) { this.deliveryCost = deliveryCost; }
    public void markAsPaid() { this.isPaid = true; }

    @Override
    public String toString() {
        return String.format(
            "Customer Order ID: %s\n" +
            "Distributor Order ID: %s\n" +
            "Goods Total: $%.2f\n" +
            "Delivery Cost: $%.2f\n" +
            "Total Cost: $%.2f\n" +
            "Paid: %s",
            customerOrderId,
            distributorOrderId == null ? "N/A" : distributorOrderId,
            goodsTotal,
            deliveryCost,
            getTotalCost(),
            isPaid ? "Yes" : "No"
        );
    }
}
