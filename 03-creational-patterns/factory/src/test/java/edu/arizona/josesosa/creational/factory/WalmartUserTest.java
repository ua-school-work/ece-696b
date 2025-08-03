package edu.arizona.josesosa.creational.factory;

import edu.arizona.josesosa.creational.factory.store.Store;
import edu.arizona.josesosa.creational.factory.store.impl.Walmart;

public class WalmartUserTest extends UserTest {
    // pick a store
    protected Store makeStore() {
        return new Walmart();
    }
}
