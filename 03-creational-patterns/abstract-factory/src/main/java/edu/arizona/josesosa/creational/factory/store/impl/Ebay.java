package edu.arizona.josesosa.creational.factory.store.impl;

import edu.arizona.josesosa.creational.abstractfactory.EbayFactory;
import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.cart.CartLineItem;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.product.impl.EbayProduct;
import edu.arizona.josesosa.creational.factory.store.Store;

import java.util.List;

public class Ebay extends Store {

    /**
     * Constructor with default factory
     */
    public Ebay() {
        super(new edu.arizona.josesosa.creational.abstractfactory.EbayFactory().makeStore().getDistributors());
    }
    
    /**
     * Constructor with distributor list
     * 
     * @param distributorList List of distributors for this store
     */
    public Ebay(List<Distributor> distributorList) {
        super(distributorList);
    }

    @Override
    protected void hookProcess(Cart order) throws Exception {
        System.out.println(Ebay.class.getSimpleName() + " is happy for your order");
        for (CartLineItem line : order.getOrderList()) {
            processLineItem(line);
        }
        System.out.println("Total: " + order.getTotal());
    }

    private void processLineItem(CartLineItem line) throws Exception {
        Product product = line.getProduct();
        printProductDetails(line, product);
        validateEbayProduct(product);
        printEbayProductRank((EbayProduct) product);
    }

    private void printProductDetails(CartLineItem line, Product product) {
        System.out.println("* " + product.getName() + " " + line.getQuantity() + "x " + product.getPrice());
    }

    private void validateEbayProduct(Product product) throws Exception {
        if (!(product instanceof EbayProduct)) {
            throw new Exception("Not an eBay product");
        }
    }

    private void printEbayProductRank(EbayProduct product) {
        System.out.println("- Rank... " + product.getRank());
    }
}