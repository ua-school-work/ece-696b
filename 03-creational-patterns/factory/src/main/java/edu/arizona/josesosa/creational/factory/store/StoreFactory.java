package edu.arizona.josesosa.creational.factory.store;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;

import java.util.List;

public abstract class StoreFactory {
    protected abstract List<Distributor> createDistributors();
}
