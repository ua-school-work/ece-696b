package edu.arizona.josesosa.creational.factory;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.product.Product;
import edu.arizona.josesosa.creational.factory.store.Store;
import edu.arizona.josesosa.creational.factory.store.impl.Ebay;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest {

    private Product soap;
    private Product anotherSoap;
    private Product tabaco;
    private Product book;
    private Product lego;

    // pick a store
    protected Store makeStore() {
        // Walmart();
        return new Ebay();
    }

    protected Product makeProduct(String name) {
        return new Product(name);
    }

    @BeforeAll
    public void setUp() {
        // pick product type
        // what if ebay product?

        soap = makeProduct("Soap").init("Nice protocol", new BigDecimal(30));
        anotherSoap = makeProduct("Soap").init("Nice protocol", new BigDecimal(30));
        tabaco = makeProduct("Tabaco").init("Dont smoke", new BigDecimal(20));
        book = makeProduct("Book").init("Read me", new BigDecimal(25));
        lego = makeProduct("Lego").init("Play me", new BigDecimal(35));

    }

    @Test
    public void test() throws Exception {
        Store store = makeStore();
        Cart cart = makeAnOrder();
        selectDistributorBasedOnRank(store);

        // process
        store.process(cart);

    }

    @Test
    public void testFail() throws Exception {

        Store store = makeStore();
        Cart cart = makeAnOrder();
        // try to process
        assertThrows(Exception.class, () -> {
            store.process(cart);
        });

    }

    @Test
    public void testAggregation() throws Exception {
        Cart cart = makeAnOrder();
        assertEquals(5, cart.getOrderList().size());
    }

    @Test
    public void testTotal() throws Exception {
        Cart cart = makeAnOrder();
        assertEquals(new BigDecimal("190"), cart.getTotal());
    }

    private Cart makeAnOrder() {
        // make an order
        Cart cart = new Cart();
        cart.addLine(soap, 2); // TODO change to fluentInterface
        cart.addLine(anotherSoap, 1);
        cart.addLine(tabaco, 2);
        cart.addLine(lego, 1);
        cart.addLine(book, 1);
        return cart;
    }

    private void selectDistributorBasedOnRank(Store store) {
        // <pick by rank> //TODO make me a strategy!
        int index = 0;
        double rank = 0;
        for (int i = 0; i < store.getDistributorList().size(); i++) {
            Distributor distributor = store.getDistributorList().get(i);
            if (distributor.getRank() > rank) {
                index = i;
                rank = distributor.getRank();
            }
            // TODO use logger!
            System.out.println("* " + distributor.getClass().getSimpleName());
        }
        store.selectDistributor(index);
    }

}
