package edu.arizona.josesosa.structural.bridge.repository;

import edu.arizona.josesosa.structural.bridge.model.DistributorOrder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderRepository {
    private final Map<String, DistributorOrder> orders = new HashMap<>();
    private final Map<String, String> customerHandleToOrderId = new HashMap<>();

    public void save(DistributorOrder order) {
        orders.put(order.getOrderId(), order);
        customerHandleToOrderId.put(order.getCustomerOrderHandle(), order.getOrderId());
    }

    public Optional<DistributorOrder> findById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    public Optional<String> findOrderIdByCustomerHandle(String customerHandle) {
        return Optional.ofNullable(customerHandleToOrderId.get(customerHandle));
    }
}
