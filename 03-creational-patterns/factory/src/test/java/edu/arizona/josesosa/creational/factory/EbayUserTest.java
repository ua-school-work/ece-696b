package edu.arizona.josesosa.creational.factory;

import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.product.impl.EbayProduct;
import edu.arizona.josesosa.creational.factory.store.Store;
import edu.arizona.josesosa.creational.factory.store.impl.Ebay;

public class EbayUserTest extends UserTest {

    // pick a store
    protected Store makeStore() {
        return new Ebay();
    }

    protected Product makeProduct(String name) {
        return new EbayProduct(name);
    }
}
