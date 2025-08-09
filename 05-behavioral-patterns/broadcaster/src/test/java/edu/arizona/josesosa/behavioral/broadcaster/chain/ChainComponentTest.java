package edu.arizona.josesosa.behavioral.broadcaster.chain;

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
 * Unit tests for the ChainComponent class.
 */
public class ChainComponentTest {

    private ChainComponent component1;
    private ChainComponent component2;
    private ChainComponent component3;
    private List<ChainComponent> allComponents;
    private String originalUserDir;
    private Path tempDir;

    @BeforeEach
    public void setUp() throws IOException {
        // Store the original user.dir property
        originalUserDir = System.getProperty("user.dir");
        
        // Create a temporary directory
        tempDir = Files.createTempDirectory("chain-component-test");
        
        // Create a logs directory in the temp directory
        Path logsDir = tempDir.resolve("logs");
        Files.createDirectories(logsDir);
        
        // Set the logs directory as the current directory for the test
        System.setProperty("user.dir", tempDir.toString());
        
        // Create a chain of components
        component1 = new ChainComponent("chain-1");
        component2 = new ChainComponent("chain-2");
        component3 = new ChainComponent("chain-3");
        
        // Connect components in a chain
        component1.setNextInChain(component2);
        component2.setNextInChain(component3);
        
        // Set all components list
        allComponents = new ArrayList<>();
        allComponents.add(component1);
        allComponents.add(component2);
        allComponents.add(component3);
        
        component1.setAllComponents(allComponents);
        component2.setAllComponents(allComponents);
        component3.setAllComponents(allComponents);
    }
    
    @AfterEach
    public void tearDown() throws InterruptedException {
        // Stop all components to clean up resources
        component1.stop();
        component2.stop();
        component3.stop();
        
        // Add a small delay to ensure log files are fully closed
        Thread.sleep(100);
        
        // Force garbage collection to release any file handles
        System.gc();
        
        // Restore the original user.dir property
        System.setProperty("user.dir", originalUserDir);
    }

    @Test
    public void testDeliverMessage_DirectMessage() {
        // Arrange
        Message message = Message.create("sender", "chain-2", MessageType.TEXT, "Direct message");
        
        // Create spy objects to verify method calls
        ChainComponent spy1 = spy(component1);
        ChainComponent spy2 = spy(component2);
        spy1.setNextInChain(spy2);
        
        // Act
        spy1.deliverMessage(message);
        
        // Assert
        // component1 should forward the message to component2
        verify(spy1, never()).receiveMessage(message);
        verify(spy2, times(1)).deliverMessage(message);
    }

    @Test
    public void testDeliverMessage_BroadcastMessage() {
        // Arrange
        Message message = Message.create("sender", "*", MessageType.TEXT, "Broadcast message");
        
        // Create spy objects to verify method calls
        ChainComponent spy1 = spy(component1);
        ChainComponent spy2 = spy(component2);
        ChainComponent spy3 = spy(component3);
        spy1.setNextInChain(spy2);
        spy2.setNextInChain(spy3);
        
        // Act
        spy1.deliverMessage(message);
        
        // Assert
        // All components should receive the broadcast message
        verify(spy1, times(1)).receiveMessage(message);
        verify(spy2, times(1)).deliverMessage(message);
    }

    @Test
    public void testDeliverMessage_EndOfChain() {
        // Arrange
        Message message = Message.create("sender", "non-existent", MessageType.TEXT, "Message to non-existent component");
        
        // Create a chain with no circular reference
        ChainComponent spy1 = spy(new ChainComponent("chain-1"));
        ChainComponent spy2 = spy(new ChainComponent("chain-2"));
        ChainComponent spy3 = spy(new ChainComponent("chain-3"));
        spy1.setNextInChain(spy2);
        spy2.setNextInChain(spy3);
        // spy3 has no next component
        
        // Act
        spy1.deliverMessage(message);
        
        // Assert
        // Message should be passed along the chain but not received by any component
        verify(spy1, never()).receiveMessage(message);
        verify(spy2, never()).receiveMessage(message);
        verify(spy3, never()).receiveMessage(message);
        verify(spy3, times(1)).deliverMessage(message);
    }

    @Test
    public void testSendCommandToRandomRecipient() {
        // Arrange
        Command command = mock(Command.class);
        
        // Create a spy to capture the message
        ChainComponent spy1 = spy(component1);
        
        // Act
        spy1.sendCommandToRandomRecipient(command);
        
        // Assert
        // Verify that sendMessage was called with a command message
        verify(spy1, times(1)).sendMessage(
            argThat(recipientId -> !recipientId.equals("chain-1") && allComponents.stream().anyMatch(c -> c.getId().equals(recipientId))),
            eq(MessageType.COMMAND),
            eq(command)
        );
    }

    @Test
    public void testSendTextToRandomRecipient() {
        // Arrange
        String text = "Test text message";
        
        // Create a spy to capture the message
        ChainComponent spy1 = spy(component1);
        
        // Act
        spy1.sendTextToRandomRecipient(text);
        
        // Assert
        // Verify that sendMessage was called with a text message
        verify(spy1, times(1)).sendMessage(
            argThat(recipientId -> !recipientId.equals("chain-1") && allComponents.stream().anyMatch(c -> c.getId().equals(recipientId))),
            eq(MessageType.TEXT),
            eq(text)
        );
    }

    @Test
    public void testGetNextInChain() {
        // Assert
        assertThat(component1.getNextInChain()).isEqualTo(component2);
        assertThat(component2.getNextInChain()).isEqualTo(component3);
        assertThat(component3.getNextInChain()).isNull();
    }
}