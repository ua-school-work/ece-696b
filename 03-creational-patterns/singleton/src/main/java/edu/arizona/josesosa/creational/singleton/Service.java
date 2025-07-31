package edu.arizona.josesosa.creational.singleton;

import java.util.Date;

/**
 * Interface for the services
 */
public interface Service {

    /**
     * Calculates taxes
     *
     * @param amount
     * @return taxed amount
     */
    public double taxCalculation(double amount);

    /**
     * Tracking the totals
     *
     * @param id
     * @param amount
     */
    public void registerSale(int id, double amount);

    /**
     * reporting of given day
     *
     * @param day
     * @return the total sale sum
     */
    public double dailyReport(Date day);

    /**
     * Time stamp
     *
     * @return now
     */
    public Date now();
}
