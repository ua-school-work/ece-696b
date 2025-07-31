package edu.arizona.josesosa.creational.singleton.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A thread-safe singleton class for recording sales data.
 * This class ensures that only one instance of the register exists
 * and that all write and read operations are synchronized to prevent
 * corruption in a multithreaded environment.
 */
final class Register {

    private final Map<Date, Double> register = new HashMap<>();
    private final Map<Date, Map<Integer, Double>> registerDetails = new HashMap<>();

    // 1. Private constructor to prevent direct instantiation.
    private Register() {}

    // 2. The Initialization-on-demand Holder idiom for lazy, thread-safe initialization.
    private static final class InstanceHolder {
        private static final Register INSTANCE = new Register();
    }

    // 3. Public static method to get the singleton instance.
    public static Register getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Adds a sale record. This method is synchronized to ensure that
     * concurrent calls from multiple threads do not corrupt the register's state.
     *
     * @param date The date of the sale.
     * @param id The ID of the sale.
     * @param amount The amount of the sale.
     */
    public synchronized void add(Date date, int id, Double amount) {
        // Use merge to atomically update the total for the given date.
        register.merge(date, amount, Double::sum);

        // Use computeIfAbsent to ensure the map for the date exists, then add the detail.
        registerDetails.computeIfAbsent(date, k -> new HashMap<>()).put(id, amount);
    }

    /**
     * Gets the total sales for a given date. This method is synchronized
     * to ensure it reads a consistent state and doesn't conflict with a write operation.
     *
     * @param date The date for which to retrieve the total.
     * @return The total sales amount for the date.
     */
    public synchronized Double total(Date date) {
        return register.get(date);
    }
}