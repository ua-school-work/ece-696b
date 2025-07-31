package edu.arizona.josesosa.creational.singleton.impl;

import edu.arizona.josesosa.creational.singleton.Logger;
import edu.arizona.josesosa.creational.singleton.Service;

import java.util.Calendar;
import java.util.Date;

public class ServiceImpl implements Service {

    Logger log = LoggerImpl.getInstance();
    Register register = Register.getInstance();

    // 1. Private constructor to prevent instantiation
    private ServiceImpl() {
        sleep(2000);
        log.log(ServiceImpl.class, "initiated");
    }

    /** 2. Static inner class to hold the singleton instance.
           This class is loaded only when getInstance() is called,
           ensuring lazy initialization and thread safety without synchronization overhead.
           This is the "Initialization-on-demand holder idiom". **/
    private static final class ServiceImplHolder {
        private static final ServiceImpl INSTANCE = new ServiceImpl();
    }

    /** 3. Public method to provide access to the singleton instance.
           This method is thread-safe and ensures that only one instance of ServiceImpl is created. **/
    public static ServiceImpl getInstance() {
        return ServiceImplHolder.INSTANCE;
    }

    public double taxCalculation(double amount) {
        sleep(200);
        log.log(ServiceImpl.class, "taxCalculation");
        return amount * 1.21;
    }

    public void registerSale(int id, double amount) {
        // issue registration
        sleep(50);
        register.add(now(), id, amount); // it could be better
        log.log(ServiceImpl.class, "registerSale " + id);
    }

    public double dailyReport(Date day) {
        sleep(1000);
        log.log(ServiceImpl.class, "dailyReport");
        return register.total(now()); // it could be better
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } // simulation
    }

    public Date now() {
        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
