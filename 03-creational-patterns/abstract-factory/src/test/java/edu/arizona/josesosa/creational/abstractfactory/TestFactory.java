package edu.arizona.josesosa.creational.abstractfactory;

import edu.arizona.josesosa.creational.factory.DefaultTestStore;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.distributor.impl.DHL;
import edu.arizona.josesosa.creational.factory.distributor.impl.DPD;
import edu.arizona.josesosa.creational.factory.distributor.impl.UPS;
import edu.arizona.josesosa.creational.factory.distributor.impl.USPS;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.store.Store;

import java.util.List;

/**
 * Test implementation of AbstractFactory for use in tests.
 * Creates standard products and a test store.
 */
public class TestFactory extends AbstractFactory {

    @Override
    protected List<Distributor> makeDistributorList() {
        return List.of(
            new DHL(),
            new DPD(),
            new USPS(),
            new UPS()
        );
    }

    @Override
    protected Product doMakeProduct(String name) {
        return new Product(name);
    }

    @Override
    public Store makeStore() {
        return new DefaultTestStore(makeDistributorList());
    }
}