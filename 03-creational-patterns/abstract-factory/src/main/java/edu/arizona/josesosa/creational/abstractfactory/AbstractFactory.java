package edu.arizona.josesosa.creational.abstractfactory;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.store.Store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract Factory that creates products, stores, and distributors.
 * Implements the Flyweight pattern for product caching.
 */
public abstract class AbstractFactory {
    
    private final Map<String, Product> cache = new HashMap<>();
    
    /**
     * Creates a list of distributors for the store.
     * 
     * @return List of distributors
     */
    protected abstract List<Distributor> makeDistributorList();
    
    /**
     * Creates a product with the given name.
     * This method is renamed from makeProduct to doMakeProduct as part of the Flyweight pattern.
     * 
     * @param name The name of the product
     * @return A new product instance
     */
    protected abstract Product doMakeProduct(String name);
    
    /**
     * Creates a store instance.
     * 
     * @return A new store instance
     */
    public abstract Store makeStore();
    
    /**
     * Creates a product with the given name, using the Flyweight pattern.
     * If a product with the given name already exists in the cache, it is returned.
     * Otherwise, a new product is created and added to the cache.
     * 
     * @param name The name of the product
     * @return A product instance
     */
    public final Product makeProduct(String name) {
        if (cache.containsKey(name)) {
            return cache.get(name);
        } else {
            Product product = doMakeProduct(name);
            cache.put(name, product);
            return product;
        }
    }
}