package edu.arizona.josesosa.creational.factory.store;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.distributor.strategy.DeliveryPickStrategy;
import edu.arizona.josesosa.creational.factory.distributor.strategy.PickByPrice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Store {

    private final List<Cart> orderHistory = new ArrayList<>();
    private final List<Distributor> distributors;
    private Distributor selectedDistributor;
    private DeliveryPickStrategy deliveryPickStrategy;

    /**
     * Constructs a Store using a factory to supply its dependencies.
     * Uses PickByPrice as the default strategy.
     *
     * @param storeFactory The factory for creating distributors.
     */
    public Store(StoreFactory storeFactory) {
        this.distributors = createDistributorsFromFactory(storeFactory);
        this.deliveryPickStrategy = new PickByPrice(); // Default strategy
    }
    
    /**
     * Constructs a Store using a factory to supply its dependencies
     * and a specific delivery pick strategy.
     *
     * @param storeFactory The factory for creating distributors.
     * @param deliveryPickStrategy The strategy to use for selecting distributors.
     */
    public Store(StoreFactory storeFactory, DeliveryPickStrategy deliveryPickStrategy) {
        this.distributors = createDistributorsFromFactory(storeFactory);
        this.deliveryPickStrategy = deliveryPickStrategy;
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
     * Sets the strategy for picking distributors.
     *
     * @param strategy The strategy to use for selecting distributors.
     */
    public void setDeliveryPickStrategy(DeliveryPickStrategy strategy) {
        this.deliveryPickStrategy = Objects.requireNonNull(strategy, "DeliveryPickStrategy cannot be null");
    }
    
    /**
     * Gets the current strategy for picking distributors.
     *
     * @return The current delivery pick strategy.
     */
    public DeliveryPickStrategy getDeliveryPickStrategy() {
        return deliveryPickStrategy;
    }
    
    /**
     * Selects a distributor using the current delivery pick strategy.
     * This automatically selects the best distributor according to the strategy's criteria.
     */
    public void selectDistributorUsingStrategy() {
        if (deliveryPickStrategy == null) {
            throw new IllegalStateException("No delivery pick strategy has been set");
        }
        this.selectedDistributor = deliveryPickStrategy.pickDistributor(distributors);
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