package edu.arizona.josesosa.creational.singleton;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A thread-safe singleton class for generating unique sale IDs.
 * It uses the Initialization-on-demand Holder idiom for lazy initialization
 * and an AtomicInteger to ensure that each call to getNext() returns a
 * unique value even in a multithreaded context.
 */
public final class SaleCounter {

    private final AtomicInteger counter = new AtomicInteger(0);

    // 1. Private constructor to prevent direct instantiation.
    private SaleCounter() {}

    // 2. The Initialization-on-demand Holder for a safe, lazy singleton.
    private static final class InstanceHolder {
        private static final SaleCounter INSTANCE = new SaleCounter();
    }

    // 3. Public static method to get the singleton instance.
    public static SaleCounter getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Atomically increments the counter and returns the new value.
     * This operation is thread-safe.
     * @return The next unique sale ID.
     */
    public int getNext() {
        return counter.getAndIncrement();
    }
}