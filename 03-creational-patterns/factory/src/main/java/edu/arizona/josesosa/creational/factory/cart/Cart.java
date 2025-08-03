package edu.arizona.josesosa.creational.factory.cart;

import edu.arizona.josesosa.creational.factory.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Cart {

    private final List<CartLineItem> orderList = new ArrayList<>();

    /**
     * Add a product to the cart. If the product already exists, its quantity is updated.
     *
     * @param product  the product to add
     * @param quantity the quantity of the product
     * @return the cart itself
     */
    public Cart addLine(Product product, int quantity) {
        findLineItemBy(product).ifPresentOrElse(
                lineItem -> lineItem.setQuantity(lineItem.getQuantity() + quantity),
                () -> orderList.add(CartLineItem.make(product, quantity))
        );
        return this;
    }

    private Optional<CartLineItem> findLineItemBy(Product product) {
        return orderList.stream()
                .filter(line -> line.getProduct() == product)
                .findFirst();
    }

    /**
     * @return The total cart cost
     */
    public BigDecimal getTotal() {
        return orderList.stream()
                .map(CartLineItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * @return An unmodifiable list of the items in the cart.
     */
    public List<CartLineItem> getOrderList() {
        return Collections.unmodifiableList(orderList);
    }
}