package edu.arizona.josesosa.creational.singleton.impl;

import edu.arizona.josesosa.creational.singleton.Logger;
import java.util.ArrayList;
import java.util.List;

public class LoggerImpl implements Logger {

    private static int counter = 1;
    private int id;
    private Class<?> c;
    private List<String> files = new ArrayList<>();

    private LoggerImpl() {
        this.c = c;
        id = counter++;
        log(LoggerImpl.class, "Made logger " + id);
    }

    /** 1. Private constructor to prevent instantiation.
        This ensures that the LoggerImpl can only be instantiated once. **/
    private static final class LoggerImplHolder {
        private static final LoggerImpl INSTANCE = new LoggerImpl();
    }

    /** 2. Static inner class to hold the singleton instance.
        This class is loaded only when getInstance() is called,
        ensuring lazy initialization and thread safety without synchronization overhead.
        This is the "Initialization-on-demand holder idiom". **/
    public static LoggerImpl getInstance() {
        return LoggerImplHolder.INSTANCE;
    }

    /* (non-Javadoc)
     * @see cz.cvut.fel.cs.ass.Logger#log(java.lang.String)
     */
    @Override
    public void log(Class<?> c, String s) {
        // 3. Changed signature to make this a true singleton. Left additional comments on the interface file.
        files.add(s);
        System.out.println("Logging [" + c.getSimpleName() + "#" + id + "]: " + s);
    }


}
