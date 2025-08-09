package edu.arizona.josesosa.behavioral.broadcaster.observer;

import edu.arizona.josesosa.behavioral.broadcaster.common.Message;

/**
 * Subject interface for the Observer pattern.
 * Subjects can register observers and notify them of events.
 */
public interface Subject {
    
    /**
     * Register an observer with this subject
     * 
     * @param observer The observer to register
     */
    void registerObserver(Observer observer);
    
    /**
     * Remove an observer from this subject
     * 
     * @param observer The observer to remove
     */
    void removeObserver(Observer observer);
    
    /**
     * Notify all registered observers of a message
     * 
     * @param message The message to notify observers about
     */
    void notifyObservers(Message message);
    
    /**
     * Get the ID of this subject
     * 
     * @return The subject's ID
     */
    String getId();
}