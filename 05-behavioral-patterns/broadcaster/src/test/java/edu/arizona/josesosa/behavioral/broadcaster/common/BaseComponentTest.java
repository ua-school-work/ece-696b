package edu.arizona.josesosa.behavioral.broadcaster.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the BaseComponent class.
 */
public class BaseComponentTest {

    private TestComponent component;
    private static final String COMPONENT_ID = "test-component";
    private String originalUserDir;
    private Path tempDir;

    @BeforeEach
    public void setUp() throws IOException {
        // Store the original user.dir property
        originalUserDir = System.getProperty("user.dir");
        
        // Create a temporary directory
        tempDir = Files.createTempDirectory("base-component-test");
        
        // Create a logs directory in the temp directory
        Path logsDir = tempDir.resolve("logs");
        Files.createDirectories(logsDir);
        
        // Set the logs directory as the current directory for the test
        System.setProperty("user.dir", tempDir.toString());
        
        // Create a test component
        component = new TestComponent(COMPONENT_ID);
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        // Stop the component to clean up resources
        component.stop();
        
        // Add a small delay to ensure log files are fully closed
        Thread.sleep(100);
        
        // Force garbage collection to release any file handles
        System.gc();
        
        // Reset the current directory to the original value
        System.setProperty("user.dir", originalUserDir);
    }

    @Test
    public void testGetId() {
        // Assert
        assertThat(component.getId()).isEqualTo(COMPONENT_ID);
    }

    @Test
    public void testSendMessage() {
        // Arrange
        String recipientId = "recipient-1";
        MessageType type = MessageType.TEXT;
        String payload = "Test message";

        // Act
        component.sendMessage(recipientId, type, payload);

        // Assert
        assertThat(component.getLastMessage()).isNotNull();
        assertThat(component.getLastMessage().getSenderId()).isEqualTo(COMPONENT_ID);
        assertThat(component.getLastMessage().getRecipientId()).isEqualTo(recipientId);
        assertThat(component.getLastMessage().getType()).isEqualTo(type);
        assertThat(component.getLastMessage().getPayload()).isEqualTo(payload);
    }

    @Test
    public void testReceiveMessage_TextMessage() {
        // Arrange
        Message message = Message.create("sender-1", COMPONENT_ID, MessageType.TEXT, "Text message");

        // Act
        component.receiveMessage(message);

        // Assert
        assertThat(component.getProcessedTextMessage()).isTrue();
    }

    @Test
    public void testReceiveMessage_CommandMessage() {
        // Arrange
        Command command = mock(Command.class);
        when(command.execute()).thenReturn("Command executed");
        Message message = Message.create("sender-1", COMPONENT_ID, MessageType.COMMAND, command);

        // Act
        component.receiveMessage(message);

        // Assert
        verify(command, times(1)).execute();
    }

    @Test
    public void testReceiveMessage_NotForThisComponent() {
        // Arrange
        Message message = Message.create("sender-1", "other-component", MessageType.TEXT, "Text message");

        // Act
        component.receiveMessage(message);

        // Assert
        assertThat(component.getProcessedTextMessage()).isFalse();
    }

    @Test
    public void testStartAndStop() throws InterruptedException {
        // Act
        component.start();
        
        // Assert that the component is running
        assertThat(component.isRunning()).isTrue();
        
        // Wait a bit to allow scheduled messages to be sent
        Thread.sleep(100);
        
        // Stop the component
        component.stop();
        
        // Assert that the component is stopped
        assertThat(component.isRunning()).isFalse();
    }

    @Test
    public void testLogFileCreation() throws IOException {
        // Arrange & Act - component is created in setUp()
        
        // Assert
        File logFile = new File(tempDir.toFile(), "logs/" + COMPONENT_ID + ".log");
        assertThat(logFile.exists()).isTrue();
    }

    /**
     * Concrete implementation of BaseComponent for testing.
     */
    private static class TestComponent extends BaseComponent {
        private Message lastMessage;
        private final AtomicBoolean processedTextMessage = new AtomicBoolean(false);

        public TestComponent(String id) {
            super(id);
        }

        @Override
        protected void deliverMessage(Message message) {
            this.lastMessage = message;
        }

        @Override
        protected void sendCommandToRandomRecipient(Command command) {
            sendMessage("random-recipient", MessageType.COMMAND, command);
        }

        @Override
        protected void sendTextToRandomRecipient(String text) {
            sendMessage("random-recipient", MessageType.TEXT, text);
        }

        @Override
        public void receiveMessage(Message message) {
            // Only set processedTextMessage if the message is for this component
            if (message.isTextMessage() && message.isForRecipient(getId())) {
                processedTextMessage.set(true);
            }
            super.receiveMessage(message);
        }

        public Message getLastMessage() {
            return lastMessage;
        }

        public boolean getProcessedTextMessage() {
            return processedTextMessage.get();
        }

        public boolean isRunning() {
            return running.get();
        }
    }
}