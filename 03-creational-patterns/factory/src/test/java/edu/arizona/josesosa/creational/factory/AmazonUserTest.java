package edu.arizona.josesosa.creational.factory;

import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.product.impl.AmazonProduct;
import edu.arizona.josesosa.creational.factory.store.Store;
import edu.arizona.josesosa.creational.factory.store.impl.Amazon;

public class AmazonUserTest extends UserTest{
    protected Store makeStore() {
        return new Amazon();
    }
    
    @Override
    protected Product makeProduct(String name) {
        return new AmazonProduct(name);
    }
}
