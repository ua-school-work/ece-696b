package edu.arizona.josesosa.creational.factory;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.store.Store;
import edu.arizona.josesosa.creational.factory.store.StoreFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

public class DefaultTestStore extends Store {
    public DefaultTestStore() {
        super(new StoreFactory() {
            @Override
            protected List<Distributor> createDistributors() {
                return List.of(new Distributor() {
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
                });
            }
        });
    }
}
