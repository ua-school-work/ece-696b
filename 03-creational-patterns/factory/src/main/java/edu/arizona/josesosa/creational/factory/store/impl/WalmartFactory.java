package edu.arizona.josesosa.creational.factory.store.impl;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.distributor.impl.DPD;
import edu.arizona.josesosa.creational.factory.distributor.impl.UPS;
import edu.arizona.josesosa.creational.factory.distributor.impl.USPS;
import edu.arizona.josesosa.creational.factory.store.StoreFactory;

import java.util.List;

public class WalmartFactory extends StoreFactory {
    @Override
    protected List<Distributor> createDistributors() {
        return List.of(new USPS(), new DPD(), new UPS());
    }
}
