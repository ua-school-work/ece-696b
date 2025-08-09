package edu.arizona.josesosa.behavioral.broadcaster.common;

/**
 * Interface for all components in the system.
 * Each component can send and receive messages.
 */
public interface Component {
    
    /**
     * Get the unique identifier of this component
     * 
     * @return The component's ID
     */
    String getId();
    
    /**
     * Send a message to another component or broadcast to all
     * 
     * @param recipientId The ID of the recipient ('*' for broadcast)
     * @param type The type of the message (TEXT or COMMAND)
     * @param payload The content of the message
     */
    void sendMessage(String recipientId, MessageType type, Object payload);
    
    /**
     * Receive a message from another component
     * 
     * @param message The message to receive
     */
    void receiveMessage(Message message);
    
    /**
     * Start the component's operation
     * Each component will send 3 messages at random times
     */
    void start();
    
    /**
     * Stop the component's operation
     */
    void stop();
}