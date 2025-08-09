package edu.arizona.josesosa.behavioral.broadcaster.observer;

import edu.arizona.josesosa.behavioral.broadcaster.common.BaseComponent;
import edu.arizona.josesosa.behavioral.broadcaster.common.Command;
import edu.arizona.josesosa.behavioral.broadcaster.common.Message;
import edu.arizona.josesosa.behavioral.broadcaster.common.MessageType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Component implementation for the Observer pattern.
 * Implements both Subject and Observer interfaces while maintaining separation of concerns.
 */
public class ObserverComponent extends BaseComponent implements Subject, Observer {
    
    private static final Random RANDOM = new Random();
    private final List<Observer> observers = new CopyOnWriteArrayList<>();
    private static List<ObserverComponent> componentRegistry = new ArrayList<>();
    
    /**
     * Creates a new observer component with the specified ID
     * 
     * @param id The unique identifier for this component
     */
    public ObserverComponent(String id) {
        super(id);
    }
    
    /**
     * Registers all components for random recipient selection
     * 
     * @param allComponents List of all components
     */
    public void setAllComponents(List<ObserverComponent> allComponents) {
        componentRegistry = new ArrayList<>(allComponents);
    }
    
    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            logMessage("Registered observer: " + observer.getId());
        }
    }
    
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        logMessage("Removed observer: " + observer.getId());
    }
    
    @Override
    public void notifyObservers(Message message) {
        for (Observer observer : observers) {
            observer.update(this, message);
        }
    }
    
    @Override
    public void update(Subject subject, Message message) {
        // When we receive an update from a subject, we handle it as a message
        receiveMessage(message);
    }
    
    @Override
    protected void deliverMessage(Message message) {
        if (!message.isBroadcast() && message.isForRecipient(getId())) {
            receiveMessage(message);
            return;
        }
        
        if (message.isBroadcast()) {
            handleBroadcastMessage(message);
        } else {
            deliverDirectMessageToObserver(message);
        }
    }
    
    /**
     * Handles broadcast messages by processing them and notifying all observers
     */
    private void handleBroadcastMessage(Message message) {
        receiveMessage(message);
        notifyObservers(message);
    }
    
    /**
     * Delivers a direct message to a specific observer if it exists
     */
    private void deliverDirectMessageToObserver(Message message) {
        for (Observer observer : observers) {
            if (observer.getId().equals(message.getRecipientId())) {
                observer.update(this, message);
                break;
            }
        }
    }
    
    @Override
    protected void sendCommandToRandomRecipient(Command command) {
        String recipientId = selectRandomRecipientId();
        sendMessage(recipientId, MessageType.COMMAND, command);
    }
    
    @Override
    protected void sendTextToRandomRecipient(String text) {
        String recipientId = selectRandomRecipientId();
        sendMessage(recipientId, MessageType.TEXT, text);
    }
    
    /**
     * Selects a random recipient ID from available components
     * 
     * @return ID of the selected recipient, or this component's ID if no others available
     */
    private String selectRandomRecipientId() {
        if (!componentRegistry.isEmpty()) {
            List<ObserverComponent> otherComponents = componentRegistry.stream()
                    .filter(c -> !c.getId().equals(getId()))
                    .toList();
            
            if (!otherComponents.isEmpty()) {
                ObserverComponent randomRecipient = otherComponents.get(RANDOM.nextInt(otherComponents.size()));
                return randomRecipient.getId();
            }
        }
        
        return getId();
    }
}