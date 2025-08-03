package edu.arizona.josesosa.creational.factory.cart;

import edu.arizona.josesosa.creational.factory.product.Product;

import java.math.BigDecimal;

/**
 * Line Item for {@link Cart}}
 */
public class CartLineItem {

    private Product product;
    private int quantity;

    public CartLineItem(Product product, int quantity) {
        super();
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubTotal() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

    public static CartLineItem make(Product product, int amount) {
        return new CartLineItem(product, amount);
    }

}
