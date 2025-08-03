package edu.arizona.josesosa.creational.factory.cart;

import edu.arizona.josesosa.creational.factory.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartLineItem> orderList = new ArrayList<>();

    /**
     * Add a product to the cart
     *
     * @param product
     * @param quantity
     */
    public void addLine(Product product, int quantity) {
        // aggregation
        for (CartLineItem line : orderList) {
            if (line.getProduct() == product) { // left == on purpose!
                line.setQuantity(line.getQuantity() + quantity);
                return;
            }
        }

        orderList.add(CartLineItem.make(product, quantity));
    }

    /**
     * @return The total cart cost
     */
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartLineItem orderLine : orderList) {
            total = total.add(orderLine.getSubTotal());
        }
        return total;
    }

    /**
     * @return get all items as a list
     */
    public List<CartLineItem> getOrderList() {
        return orderList;
    }
}
