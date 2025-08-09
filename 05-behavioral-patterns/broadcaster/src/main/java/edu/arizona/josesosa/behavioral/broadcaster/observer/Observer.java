package edu.arizona.josesosa.behavioral.broadcaster.observer;

import edu.arizona.josesosa.behavioral.broadcaster.common.Message;

/**
 * Observer interface for the Observer pattern.
 * Observers can receive updates from subjects they are subscribed to.
 */
public interface Observer {
    
    /**
     * Update method called by subjects when they have a new message
     * 
     * @param subject The subject that sent the update
     * @param message The message being sent
     */
    void update(Subject subject, Message message);
    
    /**
     * Get the ID of this observer
     * 
     * @return The observer's ID
     */
    String getId();
}