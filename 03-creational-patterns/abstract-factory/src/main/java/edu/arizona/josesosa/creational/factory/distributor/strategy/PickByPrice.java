package edu.arizona.josesosa.creational.factory.distributor.strategy;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * Strategy implementation that selects a distributor based on the lowest price.
 */
public class PickByPrice implements DeliveryPickStrategy {

    /**
     * Selects the distributor with the lowest charge from the provided list.
     *
     * @param distributors List of available distributors to choose from
     * @return The distributor with the lowest charge
     * @throws IllegalArgumentException if the distributors list is null or empty
     */
    @Override
    public Distributor pickDistributor(List<Distributor> distributors) {
        if (distributors == null || distributors.isEmpty()) {
            throw new IllegalArgumentException("Distributors list cannot be null or empty");
        }
        
        return distributors.stream()
                .min(Comparator.comparing(Distributor::getCharge))
                .orElseThrow(() -> new IllegalStateException("Could not find a distributor with the lowest price"));
    }
}