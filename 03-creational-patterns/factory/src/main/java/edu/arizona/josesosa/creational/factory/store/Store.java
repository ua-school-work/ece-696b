package edu.arizona.josesosa.creational.factory.store;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;

import java.util.ArrayList;
import java.util.List;

public abstract class Store {

    private List<Cart> orderList = new ArrayList<Cart>();

    private Cart order = null;
    private Distributor distributor = null;

    /**
     * @return list of distributors the store supports
     */
    public abstract List<Distributor> getDistributorList(); // force to override

    protected void hookProcess(Cart order) throws Exception {
    } // voluntary override

    final protected Distributor getSelectedDistributor() {
        return distributor;
    }

    /**
     * Bit imperfect selection of the distributor
     *
     * @param index of the distributor in the list
     */
    final public void selectDistributor(int index) {
        distributor = getDistributorList().get(index);

    }

    /**
     * Processing the order
     *
     * @param order
     * @throws Exception
     */
    final public void process(Cart order) throws Exception {
        if (distributor == null) {
            throw new Exception("Select distributor");
        }
        hookProcess(order);

        this.order = order;
        distributor.ship(this.order);
        orderList.add(this.order);
    }

}
