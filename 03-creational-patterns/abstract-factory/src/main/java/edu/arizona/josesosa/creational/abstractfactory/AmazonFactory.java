package edu.arizona.josesosa.creational.abstractfactory;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.distributor.impl.UPS;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.product.impl.AmazonProduct;
import edu.arizona.josesosa.creational.factory.store.Store;
import edu.arizona.josesosa.creational.factory.store.impl.Amazon;

import java.util.List;

/**
 * Concrete implementation of AbstractFactory for Amazon.
 * Binds Amazon store with AmazonProduct and UPS distributor.
 */
public class AmazonFactory extends AbstractFactory {

    @Override
    protected List<Distributor> makeDistributorList() {
        return List.of(new UPS());
    }

    @Override
    protected Product doMakeProduct(String name) {
        return AmazonProduct.make(name);
    }

    @Override
    public Store makeStore() {
        // Pass the distributor list to the store
        return new Amazon(makeDistributorList());
    }
}