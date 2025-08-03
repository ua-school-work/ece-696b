package edu.arizona.josesosa.creational.abstractfactory;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.distributor.impl.UPS;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.product.impl.AmazonProduct;
import edu.arizona.josesosa.creational.factory.store.Store;
import edu.arizona.josesosa.creational.factory.store.impl.Amazon;

import java.util.List;

/**
 * Test implementation of AbstractFactory for Amazon tests.
 * Creates Amazon products and an Amazon store.
 */
public class AmazonTestFactory extends AbstractFactory {

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
        return new Amazon(makeDistributorList());
    }
}