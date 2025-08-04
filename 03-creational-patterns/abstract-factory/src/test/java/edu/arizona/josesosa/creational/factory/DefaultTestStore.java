package edu.arizona.josesosa.creational.factory;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.store.Store;
import edu.arizona.josesosa.creational.factory.store.StoreFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

public class DefaultTestStore extends Store {
    /**
     * Constructor with default distributor
     */
    public DefaultTestStore() {
        super(List.of(new Distributor() {
            @Override
            public BigDecimal getCharge() {
                return null;
            }

            @Override
            public double getRank() {
                return 0;
            }

            @Override
            public URL getTrackingLink() throws Exception {
                return null;
            }

            @Override
            public void ship(Cart cart) throws Exception {

            }
        }));
    }
    
    /**
     * Constructor with distributor list
     * 
     * @param distributorList List of distributors for this store
     */
    public DefaultTestStore(List<Distributor> distributorList) {
        super(distributorList);
    }
}
