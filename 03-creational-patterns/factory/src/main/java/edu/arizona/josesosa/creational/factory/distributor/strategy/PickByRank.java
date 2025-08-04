package edu.arizona.josesosa.creational.factory.distributor.strategy;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Strategy implementation that selects a distributor based on the highest rank.
 */
public class PickByRank implements DeliveryPickStrategy {

    /**
     * Selects the distributor with the highest rank from the provided list.
     *
     * @param distributors List of available distributors to choose from
     * @return The distributor with the highest rank
     * @throws IllegalArgumentException if the distributors list is null or empty
     */
    @Override
    public Distributor pickDistributor(List<Distributor> distributors) {
        if (distributors == null || distributors.isEmpty()) {
            throw new IllegalArgumentException("Distributors list cannot be null or empty");
        }
        
        return distributors.stream()
                .max(Comparator.comparingDouble(Distributor::getRank))
                .orElseThrow(() -> new IllegalStateException("Could not find a distributor with the highest rank"));
    }
}