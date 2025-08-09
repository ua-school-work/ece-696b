package edu.arizona.josesosa.behavioral.broadcaster.observer;

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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ObserverComponent class.
 */
public class ObserverComponentTest {

    private ObserverComponent subject;
    private ObserverComponent observer1;
    private ObserverComponent observer2;
    private List<ObserverComponent> allComponents;
    private String originalUserDir;
    private Path tempDir;

    @BeforeEach
    public void setUp() throws IOException {
        // Store the original user.dir property
        originalUserDir = System.getProperty("user.dir");
        
        // Create a temporary directory
        tempDir = Files.createTempDirectory("observer-test");
        
        // Create a logs directory in the temp directory
        Path logsDir = tempDir.resolve("logs");
        Files.createDirectories(logsDir);
        
        // Set the logs directory as the current directory for the test
        System.setProperty("user.dir", tempDir.toString());
        
        // Create components
        subject = new ObserverComponent("observer-subject");
        observer1 = new ObserverComponent("observer-1");
        observer2 = new ObserverComponent("observer-2");
        
        // Set up observer relationships
        subject.registerObserver(observer1);
        subject.registerObserver(observer2);
        
        // Set all components list
        allComponents = new ArrayList<>();
        allComponents.add(subject);
        allComponents.add(observer1);
        allComponents.add(observer2);
        
        subject.setAllComponents(allComponents);
        observer1.setAllComponents(allComponents);
        observer2.setAllComponents(allComponents);
    }
    
    @AfterEach
    public void tearDown() throws InterruptedException {
        // Stop all components to clean up resources
        subject.stop();
        observer1.stop();
        observer2.stop();
        
        // Add a small delay to ensure log files are fully closed
        Thread.sleep(100);
        
        // Force garbage collection to release any file handles
        System.gc();
        
        // Restore the original user.dir property
        System.setProperty("user.dir", originalUserDir);
    }

    @Test
    public void testRegisterObserver() {
        // Arrange
        ObserverComponent newSubject = new ObserverComponent("new-subject");
        ObserverComponent newObserver = new ObserverComponent("new-observer");
        
        try {
            // Act
            newSubject.registerObserver(newObserver);
            
            // Assert
            // Create a message and deliver it to verify the observer receives it
            Message message = Message.create(newSubject.getId(), "*", MessageType.TEXT, "Test message");
            ObserverComponent spyObserver = spy(newObserver);
            newSubject.registerObserver(spyObserver);
            newSubject.notifyObservers(message);
            
            verify(spyObserver, times(1)).update(newSubject, message);
        } finally {
            // Clean up resources
            newSubject.stop();
            newObserver.stop();
        }
    }

    @Test
    public void testRemoveObserver() {
        // Arrange
        ObserverComponent spyObserver1 = spy(observer1);
        
        try {
            subject.registerObserver(spyObserver1);
            
            // Act
            subject.removeObserver(spyObserver1);
            
            // Assert
            // Create a message and deliver it to verify the observer doesn't receive it
            Message message = Message.create(subject.getId(), "*", MessageType.TEXT, "Test message");
            subject.notifyObservers(message);
            
            verify(spyObserver1, never()).update(subject, message);
        } finally {
            // Make sure the observer is removed
            subject.removeObserver(spyObserver1);
        }
    }

    @Test
    public void testNotifyObservers() {
        // Arrange
        ObserverComponent spyObserver1 = spy(observer1);
        ObserverComponent spyObserver2 = spy(observer2);
        
        try {
            subject.registerObserver(spyObserver1);
            subject.registerObserver(spyObserver2);
            
            Message message = Message.create(subject.getId(), "*", MessageType.TEXT, "Test message");
            
            // Act
            subject.notifyObservers(message);
            
            // Assert
            verify(spyObserver1, times(1)).update(subject, message);
            verify(spyObserver2, times(1)).update(subject, message);
        } finally {
            // Clean up
            subject.removeObserver(spyObserver1);
            subject.removeObserver(spyObserver2);
        }
    }

    @Test
    public void testUpdate() {
        // Arrange
        ObserverComponent spyObserver = spy(observer1);
        Message message = Message.create(subject.getId(), observer1.getId(), MessageType.TEXT, "Test message");
        
        try {
            // Act
            spyObserver.update(subject, message);
            
            // Assert
            verify(spyObserver, times(1)).receiveMessage(message);
        } finally {
            // No specific cleanup needed for this test as it doesn't create new resources
            // but we include the try-finally for consistency
        }
    }

    @Test
    public void testDeliverMessage_DirectMessage() {
        // Arrange - use a mock observer with a unique ID
        Observer mockObserver = mock(Observer.class);
        when(mockObserver.getId()).thenReturn("mock-observer");
        
        try {
            subject.registerObserver(mockObserver);
            
            // Create a message for the mock observer
            Message message = Message.create(subject.getId(), "mock-observer", MessageType.TEXT, "Direct message");
            
            // Act
            subject.deliverMessage(message);
            
            // Assert - verify the mock observer's update method was called
            verify(mockObserver, times(1)).update(subject, message);
        } finally {
            // Clean up
            subject.removeObserver(mockObserver);
        }
    }

    @Test
    public void testDeliverMessage_BroadcastMessage() {
        // Arrange
        ObserverComponent spyObserver1 = spy(observer1);
        ObserverComponent spyObserver2 = spy(observer2);
        
        try {
            subject.registerObserver(spyObserver1);
            subject.registerObserver(spyObserver2);
            
            Message message = Message.create(subject.getId(), "*", MessageType.TEXT, "Broadcast message");
            
            // Act
            subject.deliverMessage(message);
            
            // Assert
            verify(spyObserver1, times(1)).update(subject, message);
            verify(spyObserver2, times(1)).update(subject, message);
        } finally {
            // Clean up
            subject.removeObserver(spyObserver1);
            subject.removeObserver(spyObserver2);
        }
    }

    @Test
    public void testSendCommandToRandomRecipient() {
        // Arrange
        Command command = mock(Command.class);
        
        // Create a spy to capture the message
        ObserverComponent spy = spy(subject);
        
        try {
            // Act
            spy.sendCommandToRandomRecipient(command);
            
            // Assert
            // Verify that sendMessage was called with a command message
            verify(spy, times(1)).sendMessage(
                argThat(recipientId -> !recipientId.equals(subject.getId()) && allComponents.stream().anyMatch(c -> c.getId().equals(recipientId))),
                eq(MessageType.COMMAND),
                eq(command)
            );
        } finally {
            // No specific cleanup needed for this test as it doesn't create new resources
            // but we include the try-finally for consistency
        }
    }

    @Test
    public void testSendTextToRandomRecipient() {
        // Arrange
        String text = "Test text message";
        
        // Create a spy to capture the message
        ObserverComponent spy = spy(subject);
        
        try {
            // Act
            spy.sendTextToRandomRecipient(text);
            
            // Assert
            // Verify that sendMessage was called with a text message
            verify(spy, times(1)).sendMessage(
                argThat(recipientId -> !recipientId.equals(subject.getId()) && allComponents.stream().anyMatch(c -> c.getId().equals(recipientId))),
                eq(MessageType.TEXT),
                eq(text)
            );
        } finally {
            // No specific cleanup needed for this test as it doesn't create new resources
            // but we include the try-finally for consistency
        }
    }
}