package edu.arizona.josesosa.creational.factory.store;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Store {

    private final List<Cart> orderHistory = new ArrayList<>();
    private final List<Distributor> distributors;
    private Distributor selectedDistributor;

    /**
     * Constructs a Store using a factory to supply its dependencies.
     *
     * @param storeFactory The factory for creating distributors.
     */
    public Store(StoreFactory storeFactory) {
        this.distributors = createDistributorsFromFactory(storeFactory);
    }

    private List<Distributor> createDistributorsFromFactory(StoreFactory storeFactory) {
        Objects.requireNonNull(storeFactory, "StoreFactory cannot be null.");
        List<Distributor> createdDistributors = storeFactory.createDistributors();
        if (createdDistributors == null || createdDistributors.isEmpty()) {
            throw new IllegalArgumentException("Distributors list from StoreFactory cannot be null or empty.");
        }
        return createdDistributors;
    }

    /**
     * Returns the list of distributors the store supports.
     *
     * @return A list of supported distributors.
     */
    public List<Distributor> getDistributors() {
        return distributors;
    }

    /**
     * A hook method for subclasses to add custom logic before order processing.
     * This is a voluntary override.
     *
     * @param order The cart being processed.
     * @throws Exception if an error occurs during the hook processing.
     */
    protected void hookProcess(Cart order) throws Exception {
    } // voluntary override

    /**
     * Gets the currently selected distributor.
     *
     * @return The selected distributor.
     */
    final protected Distributor getSelectedDistributor() {
        return selectedDistributor;
    }

    /**
     * Selects a distributor from the available list by its index.
     *
     * @param index The index of the distributor in the list.
     */
    final public void selectDistributor(int index) {
        this.selectedDistributor = distributors.get(index);
    }

    /**
     * Processes the given order by shipping it via the selected distributor
     * and recording it in the history.
     *
     * @param cart The cart to be processed.
     * @throws Exception if a distributor is not selected or if shipping fails.
     */
    final public void process(Cart cart) throws Exception {
        if (selectedDistributor == null) {
            throw new IllegalStateException("A distributor must be selected before processing an order.");
        }
        hookProcess(cart);
        selectedDistributor.ship(cart);
        orderHistory.add(cart);
    }

}