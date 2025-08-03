package edu.arizona.josesosa.creational.factory.store;

import edu.arizona.josesosa.creational.factory.cart.Cart;
import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import edu.arizona.josesosa.creational.factory.distributor.strategy.DeliveryPickStrategy;
import edu.arizona.josesosa.creational.factory.distributor.strategy.PickByPrice;
import edu.arizona.josesosa.creational.factory.distributor.strategy.PickByRank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoreStrategyTest {

    private TestStore store;
    private Distributor lowPriceDistributor;
    private Distributor highRankDistributor;

    @BeforeEach
    void setUp() {
        // Create test distributors with different prices and ranks
        lowPriceDistributor = new TestDistributor("Low Price", new BigDecimal("5.00"), 3.0);
        highRankDistributor = new TestDistributor("High Rank", new BigDecimal("10.00"), 5.0);
        
        // Create a test store with these distributors
        store = new TestStore(lowPriceDistributor, highRankDistributor);
    }

    @Test
    void defaultStrategy_shouldBePickByPrice() {
        // Assert that the default strategy is PickByPrice
        assertTrue(store.getDeliveryPickStrategy() instanceof PickByPrice);
    }

    @Test
    void selectDistributorUsingStrategy_withPickByPrice_shouldSelectLowestPriceDistributor() {
        // Arrange - default strategy is PickByPrice
        
        // Act
        store.selectDistributorUsingStrategy();
        
        // Assert
        assertEquals(lowPriceDistributor, store.getSelectedDistributorForTest());
    }

    @Test
    void selectDistributorUsingStrategy_withPickByRank_shouldSelectHighestRankDistributor() {
        // Arrange
        store.setDeliveryPickStrategy(new PickByRank());
        
        // Act
        store.selectDistributorUsingStrategy();
        
        // Assert
        assertEquals(highRankDistributor, store.getSelectedDistributorForTest());
    }

    @Test
    void process_shouldUseSelectedDistributor() throws Exception {
        // Arrange
        store.selectDistributorUsingStrategy(); // Use default PickByPrice strategy
        Cart cart = new Cart();
        
        // Act
        store.process(cart);
        
        // Assert
        TestDistributor selectedDistributor = (TestDistributor) store.getSelectedDistributorForTest();
        assertTrue(selectedDistributor.wasShipCalled());
    }

    @Test
    void setDeliveryPickStrategy_shouldChangeStrategy() {
        // Arrange
        DeliveryPickStrategy newStrategy = new PickByRank();
        
        // Act
        store.setDeliveryPickStrategy(newStrategy);
        
        // Assert
        assertSame(newStrategy, store.getDeliveryPickStrategy());
    }

    // Test implementation of Store for testing purposes
    private static class TestStore extends Store {
        public TestStore(Distributor... distributors) {
            super(Arrays.asList(distributors));
        }
        
        // Expose the selected distributor for testing
        public Distributor getSelectedDistributorForTest() {
            return super.getSelectedDistributor();
        }
    }

    // Test implementation of Distributor for testing purposes
    private static class TestDistributor extends Distributor {
        private final String name;
        private final BigDecimal charge;
        private final double rank;
        private boolean shipCalled = false;

        public TestDistributor(String name, BigDecimal charge, double rank) {
            this.name = name;
            this.charge = charge;
            this.rank = rank;
        }

        public String getName() {
            return name;
        }

        public boolean wasShipCalled() {
            return shipCalled;
        }

        @Override
        public BigDecimal getCharge() {
            return charge;
        }

        @Override
        public double getRank() {
            return rank;
        }

        @Override
        public URL getTrackingLink() throws Exception {
            return new URL("https://example.com/tracking");
        }

        @Override
        public void ship(Cart cart) throws Exception {
            shipCalled = true;
        }
    }
}