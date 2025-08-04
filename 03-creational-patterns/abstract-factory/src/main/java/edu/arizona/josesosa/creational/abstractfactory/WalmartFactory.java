package edu.arizona.josesosa.creational.abstractfactory;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.distributor.impl.DHL;
import edu.arizona.josesosa.creational.factory.distributor.impl.USPS;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.store.Store;
import edu.arizona.josesosa.creational.factory.store.impl.Walmart;

import java.util.List;

/**
 * Concrete implementation of AbstractFactory for Walmart.
 * Creates standard products and a Walmart store with DHL and USPS distributors.
 */
public class WalmartFactory extends AbstractFactory {

    @Override
    protected List<Distributor> makeDistributorList() {
        return List.of(
            new DHL(),
            new USPS()
        );
    }

    @Override
    protected Product doMakeProduct(String name) {
        return new Product(name);
    }

    @Override
    public Store makeStore() {
        return new Walmart(makeDistributorList());
    }
}