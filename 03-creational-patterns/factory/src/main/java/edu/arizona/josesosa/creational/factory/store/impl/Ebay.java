package edu.arizona.josesosa.creational.factory.store.impl;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.cart.CartLineItem;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.product.impl.EbayProduct;
import edu.arizona.josesosa.creational.factory.store.Store;

public class Ebay extends Store {

    public Ebay() {
        super(new EbayFactory());
    }

    @Override
    protected void hookProcess(Cart order) throws Exception {
        System.out.println(Ebay.class.getSimpleName() + " is happy for your order");
        for (CartLineItem line : order.getOrderList()) {
            Product product = line.getProduct();
            System.out.println("* " + product.getName() + " " + line.getQuantity() + "x " + product.getPrice());
            if (product instanceof EbayProduct) {
                System.out.println("- Rank... " + ((EbayProduct) product).getRank());
            } else {
                throw new Exception("Not a eBay product");
            }

        }
        System.out.println("Total: " + order.getTotal());

    }

}
