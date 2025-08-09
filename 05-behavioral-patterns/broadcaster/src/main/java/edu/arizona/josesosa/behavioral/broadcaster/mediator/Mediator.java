package edu.arizona.josesosa.behavioral.broadcaster.mediator;

import edu.arizona.josesosa.behavioral.broadcaster.common.Message;

/**
 * Mediator interface for the Mediator pattern.
 * The mediator coordinates communication between components.
 */
public interface Mediator {
    
    /**
     * Register a component with this mediator
     * 
     * @param component The component to register
     */
    void register(MediatorComponent component);
    
    /**
     * Send a message to components through the mediator
     * 
     * @param message The message to send
     */
    void send(Message message);
    
    /**
     * Get a random component (excluding the sender)
     * 
     * @param senderId The ID of the sender to exclude
     * @return A random component's ID
     */
    String getRandomComponentId(String senderId);
    
    /**
     * Start all components registered with this mediator
     */
    void startAll();
    
    /**
     * Stop all components registered with this mediator
     */
    void stopAll();
}