package edu.arizona.josesosa.behavioral.broadcaster.common;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class BaseComponent implements Component {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final Random RANDOM = new Random();
    private static final int MAX_SLEEP_MS = 100;
    private static final int MIN_SEND_DELAY_SEC = 1;
    private static final int MAX_SEND_DELAY_SEC = 20;
    
    @Getter
    protected final String id;
    protected final PrintWriter logWriter;
    protected final AtomicBoolean running = new AtomicBoolean(false);
    protected final AtomicInteger messagesSent = new AtomicInteger(0);
    protected final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);
    
    public BaseComponent(String id) {
        this.id = id;
        this.logWriter = initializeLogger(id);
    }
    
    private PrintWriter initializeLogger(String componentId) {
        try {
            File logFile = createLogFileInLogsDirectory(componentId);
            return new PrintWriter(new FileWriter(logFile, true), true);
        } catch (IOException e) {
            log.error("Failed to create log writer for component {}", componentId, e);
            return new PrintWriter(System.out);
        }
    }
    
    private File createLogFileInLogsDirectory(String componentId) {
        String currentDir = System.getProperty("user.dir");
        File logsDir = new File(currentDir, "logs");
        
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }
        
        return new File(logsDir, componentId + ".log");
    }
    
    protected void logMessage(String message) {
        String formattedLogEntry = createTimestampedLogEntry(message);
        writeToFileAndSystemLog(formattedLogEntry);
    }
    
    private String createTimestampedLogEntry(String message) {
        String timestamp = LocalDateTime.now().format(TIME_FORMATTER);
        return String.format("[%s] %s - %s", timestamp, id, message);
    }
    
    private void writeToFileAndSystemLog(String logEntry) {
        logWriter.println(logEntry);
        log.info(logEntry);
    }
    
    @Override
    public void sendMessage(String recipientId, MessageType type, Object payload) {
        Message message = createMessageWithThisAsSender(recipientId, type, payload);
        logOutgoingMessage(message, recipientId, payload);
        applyRealisticNetworkDelay();
        deliverMessage(message);
        trackMessageSent();
    }
    
    private Message createMessageWithThisAsSender(String recipientId, MessageType type, Object payload) {
        return Message.create(id, recipientId, type, payload);
    }
    
    private void logOutgoingMessage(Message message, String recipientId, Object payload) {
        String recipientDescription = formatRecipientForLogging(recipientId);
        logMessage(String.format("Sending %s message to %s: '%s'", 
                message.getType(), recipientDescription, payload));
    }
    
    private String formatRecipientForLogging(String recipientId) {
        return "*".equals(recipientId) ? 
                "all components (broadcast)" : "component " + recipientId;
    }
    
    private void applyRealisticNetworkDelay() {
        int delayMilliseconds = RANDOM.nextInt(MAX_SLEEP_MS);
        try {
            TimeUnit.MILLISECONDS.sleep(delayMilliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void trackMessageSent() {
        messagesSent.incrementAndGet();
    }
    
    protected abstract void deliverMessage(Message message);
    
    @Override
    @Async
    public void receiveMessage(Message message) {
        if (!isAddressedToThisComponent(message)) {
            return;
        }
        
        logIncomingMessage(message);
        handleMessageBasedOnType(message);
    }
    
    private boolean isAddressedToThisComponent(Message message) {
        return message.isForRecipient(id);
    }
    
    private void logIncomingMessage(Message message) {
        logMessage(String.format("Received %s message from component %s: '%s'", 
                message.getType(), message.getSenderId(), message.getPayloadAsString()));
    }
    
    private void handleMessageBasedOnType(Message message) {
        if (message.isTextMessage()) {
            handleTextMessage(message);
        } else if (message.isCommandMessage() && message.getPayload() instanceof Command) {
            handleCommandMessage((Command) message.getPayload());
        }
    }
    
    private void handleTextMessage(Message message) {
        logMessage(String.format("Processed TEXT message: '%s'", message.getPayloadAsString()));
    }
    
    private void handleCommandMessage(Command command) {
        executeCommandAndLogResults(command);
    }
    
    private void executeCommandAndLogResults(Command command) {
        long startTime = System.currentTimeMillis();
        
        try {
            String result = command.execute();
            logSuccessfulCommandExecution(result, calculateElapsedTimeMillis(startTime));
        } catch (Exception e) {
            logFailedCommandExecution(e, calculateElapsedTimeMillis(startTime));
        }
    }
    
    private long calculateElapsedTimeMillis(long startTime) {
        return System.currentTimeMillis() - startTime;
    }
    
    private void logSuccessfulCommandExecution(String result, long executionTimeMillis) {
        logMessage(String.format("Executed command in %d ms with result: %s", 
                executionTimeMillis, result));
    }
    
    private void logFailedCommandExecution(Exception e, long executionTimeMillis) {
        logMessage(String.format("Command execution failed after %d ms: %s", 
                executionTimeMillis, e.getMessage()));
    }
    
    @Override
    public void start() {
        if (running.compareAndSet(false, true)) {
            logMessage("Starting component");
            scheduleAutomaticMessages();
        }
    }
    
    private void scheduleAutomaticMessages() {
        resetMessageCounter();
        scheduleBroadcastTextMessage();
        scheduleCommandToRandomRecipient();
        scheduleTextToRandomRecipient();
    }
    
    private void resetMessageCounter() {
        messagesSent.set(0);
    }
    
    private void scheduleBroadcastTextMessage() {
        int delaySeconds = calculateRandomDelay();
        scheduler.schedule(() -> {
            if (running.get()) {
                sendMessage("*", MessageType.TEXT, "Broadcast text message from " + id);
            }
        }, delaySeconds, TimeUnit.SECONDS);
    }
    
    private void scheduleCommandToRandomRecipient() {
        int delaySeconds = calculateRandomDelay();
        scheduler.schedule(() -> {
            if (running.get()) {
                Command command = createSimpleDelayedCommand();
                sendCommandToRandomRecipient(command);
            }
        }, delaySeconds, TimeUnit.SECONDS);
    }
    
    private Command createSimpleDelayedCommand() {
        return () -> {
            try {
                int sleepTime = RANDOM.nextInt(MAX_SLEEP_MS);
                TimeUnit.MILLISECONDS.sleep(sleepTime);
                return "Command executed successfully after " + sleepTime + "ms";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Command execution interrupted";
            }
        };
    }
    
    private void scheduleTextToRandomRecipient() {
        int delaySeconds = calculateRandomDelay();
        scheduler.schedule(() -> {
            if (running.get()) {
                sendTextToRandomRecipient("Text message from " + id);
            }
        }, delaySeconds, TimeUnit.SECONDS);
    }
    
    private int calculateRandomDelay() {
        return MIN_SEND_DELAY_SEC + RANDOM.nextInt(MAX_SEND_DELAY_SEC - MIN_SEND_DELAY_SEC);
    }
    
    protected abstract void sendCommandToRandomRecipient(Command command);
    
    protected abstract void sendTextToRandomRecipient(String text);
    
    @Override
    public void stop() {
        if (running.compareAndSet(true, false)) {
            logMessage("Stopping component");
            shutdownSchedulerAndCloseResources();
        }
    }
    
    private void shutdownSchedulerAndCloseResources() {
        scheduler.shutdownNow();
        waitForSchedulerTermination();
        closeLogWriter();
    }
    
    private void waitForSchedulerTermination() {
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                logMessage("Scheduler did not terminate in time");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logMessage("Scheduler shutdown interrupted");
        }
    }
    
    private void closeLogWriter() {
        logWriter.close();
    }
}