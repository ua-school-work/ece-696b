package edu.arizona.josesosa.behavioral.broadcaster.mediator;

import edu.arizona.josesosa.behavioral.broadcaster.common.Command;
import edu.arizona.josesosa.behavioral.broadcaster.common.Message;
import edu.arizona.josesosa.behavioral.broadcaster.common.MessageType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the MediatorComponent class.
 */
public class MediatorComponentTest {

    private MediatorComponent component;
    private Mediator mockMediator;
    private String originalUserDir;
    private Path tempDir;

    @BeforeEach
    public void setUp() throws IOException {
        // Store the original user.dir property
        originalUserDir = System.getProperty("user.dir");
        
        // Create a temporary directory
        tempDir = Files.createTempDirectory("mediator-component-test");
        
        // Create a logs directory in the temp directory
        Path logsDir = tempDir.resolve("logs");
        Files.createDirectories(logsDir);
        
        // Set the logs directory as the current directory for the test
        System.setProperty("user.dir", tempDir.toString());
        
        // Create a component and a mock mediator
        component = new MediatorComponent("mediator-test");
        mockMediator = mock(Mediator.class);
    }
    
    @AfterEach
    public void tearDown() throws InterruptedException {
        // Stop the component to clean up resources
        component.stop();
        
        // Add a small delay to ensure log files are fully closed
        Thread.sleep(100);
        
        // Force garbage collection to release any file handles
        System.gc();
        
        // Restore the original user.dir property
        System.setProperty("user.dir", originalUserDir);
    }

    @Test
    public void testSetMediator() {
        // Act
        component.setMediator(mockMediator);
        
        // Assert
        verify(mockMediator, times(1)).register(component);
    }

    @Test
    public void testDeliverMessage() {
        // Arrange
        component.setMediator(mockMediator);
        Message message = Message.create("sender", "recipient", MessageType.TEXT, "Test message");
        
        // Act
        component.deliverMessage(message);
        
        // Assert
        verify(mockMediator, times(1)).send(message);
    }

    @Test
    public void testDeliverMessage_NoMediator() {
        // Arrange
        Message message = Message.create("sender", "recipient", MessageType.TEXT, "Test message");
        
        // Act
        component.deliverMessage(message);
        
        // Assert
        // No exception should be thrown, and no mediator method should be called
        // since the mediator is null
    }

    @Test
    public void testSendCommandToRandomRecipient() {
        // Arrange
        component.setMediator(mockMediator);
        Command command = mock(Command.class);
        String randomRecipientId = "random-recipient";
        when(mockMediator.getRandomComponentId(component.getId())).thenReturn(randomRecipientId);
        
        // Act
        component.sendCommandToRandomRecipient(command);
        
        // Assert
        verify(mockMediator, times(1)).getRandomComponentId(component.getId());
        verify(mockMediator, times(1)).send(argThat(message -> 
            message.getSenderId().equals(component.getId()) &&
            message.getRecipientId().equals(randomRecipientId) &&
            message.getType() == MessageType.COMMAND &&
            message.getPayload() == command
        ));
    }

    @Test
    public void testSendCommandToRandomRecipient_NoMediator() {
        // Arrange
        Command command = mock(Command.class);
        
        // Act
        component.sendCommandToRandomRecipient(command);
        
        // Assert
        // No exception should be thrown, and no mediator method should be called
        // since the mediator is null
    }

    @Test
    public void testSendTextToRandomRecipient() {
        // Arrange
        component.setMediator(mockMediator);
        String text = "Test text message";
        String randomRecipientId = "random-recipient";
        when(mockMediator.getRandomComponentId(component.getId())).thenReturn(randomRecipientId);
        
        // Act
        component.sendTextToRandomRecipient(text);
        
        // Assert
        verify(mockMediator, times(1)).getRandomComponentId(component.getId());
        verify(mockMediator, times(1)).send(argThat(message -> 
            message.getSenderId().equals(component.getId()) &&
            message.getRecipientId().equals(randomRecipientId) &&
            message.getType() == MessageType.TEXT &&
            message.getPayload().equals(text)
        ));
    }

    @Test
    public void testSendTextToRandomRecipient_NoMediator() {
        // Arrange
        String text = "Test text message";
        
        // Act
        component.sendTextToRandomRecipient(text);
        
        // Assert
        // No exception should be thrown, and no mediator method should be called
        // since the mediator is null
    }
}