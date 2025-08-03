package edu.arizona.josesosa.creational.factory.distributor.strategy;

import edu.arizona.josesosa.creational.factory.distributor.Distributor;

import java.util.List;

/**
 * Strategy interface for selecting a distributor from a list of available distributors.
 * Different implementations can use different criteria for selection.
 */
public interface DeliveryPickStrategy {
    
    /**
     * Selects a distributor from the provided list based on the strategy's criteria.
     *
     * @param distributors List of available distributors to choose from
     * @return The selected distributor based on the strategy's criteria
     * @throws IllegalArgumentException if the distributors list is null or empty
     */
    Distributor pickDistributor(List<Distributor> distributors);
}