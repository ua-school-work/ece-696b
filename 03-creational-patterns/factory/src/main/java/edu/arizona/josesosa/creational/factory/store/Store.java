package edu.arizona.josesosa.creational.factory.store;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;

import java.util.ArrayList;
import java.util.List;

public abstract class Store {

    private final List<Cart> orderList = new ArrayList<Cart>();
    protected List<Distributor> distributors = null;
    private Cart order = null;
    private Distributor selectedDistributor = null;

    /**
     * Constructor
     */
    public Store(StoreFactory storeFactory) {
        if (storeFactory == null) {
            throw new IllegalArgumentException("StoreFactory cannot be null");
        }
        if (storeFactory.createDistributors() == null) {
            throw new IllegalArgumentException("Distributors list from StoreFactory cannot be null");
        }
        distributors = storeFactory.createDistributors();
    }

    /**
     * @return list of distributors the store supports
     */
    public List<Distributor> getDistributorList() {
        if (distributors == null || distributors.isEmpty()) {
            throw new IllegalStateException("No distributors available");
        }
        return distributors;
    }

    protected void hookProcess(Cart order) throws Exception {
    } // voluntary override

    final protected Distributor getSelectedDistributor() {
        return selectedDistributor;
    }

    /**
     * Bit imperfect selection of the distributor
     *
     * @param index of the distributor in the list
     */
    final public void selectDistributor(int index) {
        selectedDistributor = getDistributorList().get(index);

    }

    /**
     * Processing the order
     *
     * @param order
     * @throws Exception
     */
    final public void process(Cart order) throws Exception {
        if (selectedDistributor == null) {
            throw new Exception("Select distributor");
        }
        hookProcess(order);

        this.order = order;
        selectedDistributor.ship(this.order);
        orderList.add(this.order);
    }

}
