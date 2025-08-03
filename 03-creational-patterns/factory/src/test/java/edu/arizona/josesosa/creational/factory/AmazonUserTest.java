package edu.arizona.josesosa.creational.factory;

import edu.arizona.josesosa.creational.factory.store.Store;
import edu.arizona.josesosa.creational.factory.store.impl.Amazon;

public class AmazonUserTest extends UserTest{
    protected Store makeStore() {
        return new Amazon();
    }
}
