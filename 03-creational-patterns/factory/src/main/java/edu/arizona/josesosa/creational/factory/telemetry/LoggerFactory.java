package edu.arizona.josesosa.creational.factory.telemetry;

import java.io.PrintStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerFactory {

    public static void redirectSysOutAndErrToLog() {
        configureRootLogger();
        redirectSystemStreams();
    }

    private static void configureRootLogger() {
        Logger rootLogger = Logger.getLogger("");
        if (rootLogger.getHandlers().length == 0) {
            ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(Level.ALL);
            rootLogger.addHandler(handler);
        }
        rootLogger.setLevel(Level.ALL);
    }

    private static void redirectSystemStreams() {
        // This logger will only be used as a fallback.
        Logger fallbackLogger = Logger.getLogger("redirected");
        PrintStream logStream = new PrintStream(new LoggingOutputStream(fallbackLogger, Level.INFO), true);
        System.setOut(logStream);
        System.setErr(logStream);
    }
}