package edu.arizona.josesosa.creational.factory.telemetry;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * An OutputStream that redirects output to a Logger, attributing the log
 * to the original calling class by inspecting the stack trace.
 */
public class LoggingOutputStream extends OutputStream {

    private final Logger fallbackLogger;
    private final Level level;
    private final StringBuilder buffer = new StringBuilder();

    private static final ThreadLocal<Boolean> isLogging = ThreadLocal.withInitial(() -> false);

    public LoggingOutputStream(Logger logger, Level level) {
        this.fallbackLogger = logger;
        this.level = level;
    }

    @Override
    public void write(int b) throws IOException {
        if (isLogging.get()) {
            return;
        }

        if (b == '\r' || b == '\n') {
            if (!buffer.isEmpty()) {
                logLine();
            }
        } else {
            buffer.append((char) b);
        }
    }

    @Override
    public void flush() {
        if (!buffer.isEmpty()) {
            logLine();
        }
    }

    private void logLine() {
        if (isLogging.get()) {
            return;
        }

        isLogging.set(true);
        try {
            StackTraceElement callerFrame = findCallerFrame();
            logWithCallerInfo(callerFrame, buffer.toString());
        } finally {
            buffer.setLength(0);
            isLogging.remove();
        }
    }

    private void logWithCallerInfo(StackTraceElement callerFrame, String message) {
        String callerClassName = (callerFrame != null) ? callerFrame.getClassName() : null;
        String callerMethodName = (callerFrame != null) ? callerFrame.getMethodName() : null;

        Logger logger = getLoggerFor(callerClassName);
        LogRecord record = createLogRecord(message, logger, callerClassName, callerMethodName);
        logger.log(record);
    }

    private StackTraceElement findCallerFrame() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        String loggingPackageName = LoggingOutputStream.class.getPackage().getName();

        for (StackTraceElement frame : stackTrace) {
            if (!isInternalFrame(frame.getClassName(), loggingPackageName)) {
                return frame;
            }
        }
        return null;
    }

    private boolean isInternalFrame(String className, String loggingPackageName) {
        return className.startsWith(loggingPackageName) || className.startsWith("java.") || className.startsWith("sun.");
    }

    private Logger getLoggerFor(String className) {
        return (className != null) ? Logger.getLogger(className) : this.fallbackLogger;
    }

    private LogRecord createLogRecord(String message, Logger logger, String className, String methodName) {
        LogRecord record = new LogRecord(level, message);
        record.setLoggerName(logger.getName());
        record.setSourceClassName(className);
        record.setSourceMethodName(methodName);
        return record;
    }
}