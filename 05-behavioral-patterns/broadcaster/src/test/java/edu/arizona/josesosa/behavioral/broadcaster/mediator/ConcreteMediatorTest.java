package edu.arizona.josesosa.behavioral.broadcaster.mediator;

import edu.arizona.josesosa.behavioral.broadcaster.common.Message;
import edu.arizona.josesosa.behavioral.broadcaster.common.MessageType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ConcreteMediator class.
 */
public class ConcreteMediatorTest {

    private ConcreteMediator mediator;
    private MediatorComponent component1;
    private MediatorComponent component2;
    private MediatorComponent component3;
    private String originalUserDir;
    private Path tempDir;

    @BeforeEach
    public void setUp() throws IOException {
        // Store the original user.dir property
        originalUserDir = System.getProperty("user.dir");
        
        // Create a temporary directory
        tempDir = Files.createTempDirectory("concrete-mediator-test");
        
        // Create a logs directory in the temp directory
        Path logsDir = tempDir.resolve("logs");
        Files.createDirectories(logsDir);
        
        // Set the logs directory as the current directory for the test
        System.setProperty("user.dir", tempDir.toString());
        
        // Create a mediator and components
        mediator = new ConcreteMediator();
        component1 = new MediatorComponent("mediator-1");
        component2 = new MediatorComponent("mediator-2");
        component3 = new MediatorComponent("mediator-3");
        
        // Register components with the mediator
        component1.setMediator(mediator);
        component2.setMediator(mediator);
        component3.setMediator(mediator);
    }
    
    @AfterEach
    public void tearDown() throws InterruptedException {
        // Stop all components and mediator to clean up resources
        mediator.stopAll();
        
        // Add a small delay to ensure log files are fully closed
        Thread.sleep(100);
        
        // Force garbage collection to release any file handles
        System.gc();
        
        // Restore the original user.dir property
        System.setProperty("user.dir", originalUserDir);
    }

    @Test
    public void testRegister() {
        // Arrange
        ConcreteMediator testMediator = new ConcreteMediator();
        MediatorComponent testComponent = new MediatorComponent("test-component");
        
        // Act
        testMediator.register(testComponent);
        
        // Assert
        List<MediatorComponent> components = testMediator.getAllComponents();
        assertThat(components).hasSize(1);
        assertThat(components.get(0)).isEqualTo(testComponent);
    }

    @Test
    public void testSend_DirectMessage() {
        // Arrange
        Message message = Message.create("mediator-1", "mediator-2", MessageType.TEXT, "Direct message");
        
        // Create spy objects to verify method calls
        MediatorComponent spy2 = spy(component2);
        mediator.register(spy2);
        
        // Act
        mediator.send(message);
        
        // Assert
        verify(spy2, times(1)).receiveMessage(message);
    }

    @Test
    public void testSend_BroadcastMessage() {
        // Arrange
        Message message = Message.create("mediator-1", "*", MessageType.TEXT, "Broadcast message");
        
        // Create spy objects to verify method calls
        MediatorComponent spy2 = spy(component2);
        MediatorComponent spy3 = spy(component3);
        mediator.register(spy2);
        mediator.register(spy3);
        
        // Act
        mediator.send(message);
        
        // Assert
        // Both components should receive the broadcast message (except the sender)
        verify(spy2, times(1)).receiveMessage(message);
        verify(spy3, times(1)).receiveMessage(message);
    }

    @Test
    public void testSend_RecipientNotFound() {
        // Arrange
        Message message = Message.create("mediator-1", "non-existent", MessageType.TEXT, "Message to non-existent component");
        
        // Create spy objects to verify method calls
        MediatorComponent spy2 = spy(component2);
        MediatorComponent spy3 = spy(component3);
        mediator.register(spy2);
        mediator.register(spy3);
        
        // Act
        mediator.send(message);
        
        // Assert
        // No component should receive the message
        verify(spy2, never()).receiveMessage(message);
        verify(spy3, never()).receiveMessage(message);
    }

    @Test
    public void testGetRandomComponentId() {
        // Arrange
        String senderId = "mediator-1";
        
        // Act
        String randomId = mediator.getRandomComponentId(senderId);
        
        // Assert
        // The random ID should not be the sender's ID
        assertThat(randomId).isNotEqualTo(senderId);
        // The random ID should be one of the registered components
        assertThat(randomId).isIn("mediator-2", "mediator-3");
    }

    @Test
    public void testGetRandomComponentId_OnlyOneComponent() {
        // Arrange
        ConcreteMediator testMediator = new ConcreteMediator();
        MediatorComponent testComponent = new MediatorComponent("test-component");
        testMediator.register(testComponent);
        
        // Act
        String randomId = testMediator.getRandomComponentId("test-component");
        
        // Assert
        // If there are no other components, the sender's ID should be returned
        assertThat(randomId).isEqualTo("test-component");
    }

    @Test
    public void testStartAll() {
        // Arrange
        MediatorComponent spy1 = spy(component1);
        MediatorComponent spy2 = spy(component2);
        MediatorComponent spy3 = spy(component3);
        
        ConcreteMediator testMediator = new ConcreteMediator();
        testMediator.register(spy1);
        testMediator.register(spy2);
        testMediator.register(spy3);
        
        // Act
        testMediator.startAll();
        
        // Assert
        verify(spy1, times(1)).start();
        verify(spy2, times(1)).start();
        verify(spy3, times(1)).start();
    }

    @Test
    public void testStopAll() {
        // Arrange
        MediatorComponent spy1 = spy(component1);
        MediatorComponent spy2 = spy(component2);
        MediatorComponent spy3 = spy(component3);
        
        ConcreteMediator testMediator = new ConcreteMediator();
        testMediator.register(spy1);
        testMediator.register(spy2);
        testMediator.register(spy3);
        
        // Act
        testMediator.stopAll();
        
        // Assert
        verify(spy1, times(1)).stop();
        verify(spy2, times(1)).stop();
        verify(spy3, times(1)).stop();
    }
}