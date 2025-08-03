package edu.arizona.josesosa.creational.factory.distributor.strategy;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryPickStrategyTest {

    private Distributor lowPriceDistributor;
    private Distributor highRankDistributor;
    private Distributor mediumDistributor;
    private List<Distributor> distributors;

    @BeforeEach
    void setUp() {
        // Create test distributors with different prices and ranks
        lowPriceDistributor = new TestDistributor("Low Price", new BigDecimal("5.00"), 3.0);
        highRankDistributor = new TestDistributor("High Rank", new BigDecimal("10.00"), 5.0);
        mediumDistributor = new TestDistributor("Medium", new BigDecimal("7.50"), 4.0);
        
        distributors = Arrays.asList(lowPriceDistributor, highRankDistributor, mediumDistributor);
    }

    @Test
    void pickByPrice_shouldSelectLowestPriceDistributor() {
        // Arrange
        DeliveryPickStrategy strategy = new PickByPrice();
        
        // Act
        Distributor selected = strategy.pickDistributor(distributors);
        
        // Assert
        assertEquals(lowPriceDistributor, selected);
        assertEquals("Low Price", ((TestDistributor) selected).getName());
        assertEquals(new BigDecimal("5.00"), selected.getCharge());
    }

    @Test
    void pickByRank_shouldSelectHighestRankDistributor() {
        // Arrange
        DeliveryPickStrategy strategy = new PickByRank();
        
        // Act
        Distributor selected = strategy.pickDistributor(distributors);
        
        // Assert
        assertEquals(highRankDistributor, selected);
        assertEquals("High Rank", ((TestDistributor) selected).getName());
        assertEquals(5.0, selected.getRank());
    }

    @Test
    void pickDistributor_shouldThrowException_whenDistributorsListIsNull() {
        // Arrange
        DeliveryPickStrategy priceStrategy = new PickByPrice();
        DeliveryPickStrategy rankStrategy = new PickByRank();
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> priceStrategy.pickDistributor(null));
        assertThrows(IllegalArgumentException.class, () -> rankStrategy.pickDistributor(null));
    }

    @Test
    void pickDistributor_shouldThrowException_whenDistributorsListIsEmpty() {
        // Arrange
        DeliveryPickStrategy priceStrategy = new PickByPrice();
        DeliveryPickStrategy rankStrategy = new PickByRank();
        List<Distributor> emptyList = List.of();
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> priceStrategy.pickDistributor(emptyList));
        assertThrows(IllegalArgumentException.class, () -> rankStrategy.pickDistributor(emptyList));
    }

    // Test implementation of Distributor for testing purposes
    private static class TestDistributor extends Distributor {
        private final String name;
        private final BigDecimal charge;
        private final double rank;

        public TestDistributor(String name, BigDecimal charge, double rank) {
            this.name = name;
            this.charge = charge;
            this.rank = rank;
        }

        public String getName() {
            return name;
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
        public void ship(edu.arizona.josesosa.creational.factory.cart.Cart cart) throws Exception {
            // Do nothing for test
        }
    }
}