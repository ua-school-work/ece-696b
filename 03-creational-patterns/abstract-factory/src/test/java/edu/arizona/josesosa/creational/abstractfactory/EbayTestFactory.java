package edu.arizona.josesosa.creational.abstractfactory;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.distributor.impl.DHL;
import edu.arizona.josesosa.creational.factory.distributor.impl.DPD;
import edu.arizona.josesosa.creational.factory.distributor.impl.UPS;
import edu.arizona.josesosa.creational.factory.distributor.impl.USPS;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.product.impl.EbayProduct;
import edu.arizona.josesosa.creational.factory.store.Store;
import edu.arizona.josesosa.creational.factory.store.impl.Ebay;

import java.util.List;

/**
 * Test implementation of AbstractFactory for Ebay tests.
 * Creates Ebay products and an Ebay store.
 */
public class EbayTestFactory extends AbstractFactory {

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
        return EbayProduct.make(name);
    }

    @Override
    public Store makeStore() {
        return new Ebay(makeDistributorList());
    }
}