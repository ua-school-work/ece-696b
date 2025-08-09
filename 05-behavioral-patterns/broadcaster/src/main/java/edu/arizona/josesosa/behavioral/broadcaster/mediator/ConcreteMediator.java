package edu.arizona.josesosa.behavioral.broadcaster.mediator;

import edu.arizona.josesosa.behavioral.broadcaster.common.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Concrete implementation of the Mediator interface that coordinates communication between components.
 */
@Slf4j
public class ConcreteMediator implements Mediator {
    
    private static final Random RANDOM = new Random();
    private final Map<String, MediatorComponent> components = new ConcurrentHashMap<>();
    
    @Override
    public void register(MediatorComponent component) {
        components.put(component.getId(), component);
        log.info("Component {} registered with mediator", component.getId());
    }
    
    @Override
    public void send(Message message) {
        log.debug("Mediator received message from {}: {}", message.getSenderId(), message);
        
        if (message.isBroadcast()) {
            broadcastMessage(message);
        } else {
            deliverToSpecificRecipient(message);
        }
    }
    
    /**
     * Broadcasts a message to all components except the sender
     */
    private void broadcastMessage(Message message) {
        components.values().stream()
                .filter(c -> !c.getId().equals(message.getSenderId()))
                .forEach(c -> c.receiveMessage(message));
    }
    
    /**
     * Delivers a message to a specific recipient if it exists
     */
    private void deliverToSpecificRecipient(Message message) {
        MediatorComponent recipient = components.get(message.getRecipientId());
        if (recipient != null) {
            recipient.receiveMessage(message);
        } else {
            logRecipientNotFound(message);
        }
    }
    
    /**
     * Logs a warning when a recipient cannot be found
     */
    private void logRecipientNotFound(Message message) {
        log.warn("Recipient {} not found for message from {}", 
                message.getRecipientId(), message.getSenderId());
    }
    
    @Override
    public String getRandomComponentId(String senderId) {
        List<String> otherComponentIds = findAllComponentIdsExcept(senderId);
        
        if (otherComponentIds.isEmpty()) {
            return senderId;
        }
        
        return selectRandomComponentId(otherComponentIds);
    }
    
    /**
     * Finds all component IDs except the specified one
     * 
     * @param excludedId The ID to exclude
     * @return List of component IDs excluding the specified one
     */
    private List<String> findAllComponentIdsExcept(String excludedId) {
        return components.keySet().stream()
                .filter(id -> !id.equals(excludedId))
                .toList();
    }
    
    /**
     * Selects a random component ID from the provided list
     * 
     * @param componentIds List of component IDs to choose from
     * @return A randomly selected component ID
     */
    private String selectRandomComponentId(List<String> componentIds) {
        return componentIds.get(RANDOM.nextInt(componentIds.size()));
    }
    
    @Override
    public void startAll() {
        log.info("Starting all components registered with mediator");
        activateAllComponents();
    }
    
    /**
     * Activates all registered components
     */
    private void activateAllComponents() {
        components.values().forEach(MediatorComponent::start);
    }
    
    @Override
    public void stopAll() {
        log.info("Stopping all components registered with mediator");
        deactivateAllComponents();
    }
    
    /**
     * Deactivates all registered components
     */
    private void deactivateAllComponents() {
        components.values().forEach(MediatorComponent::stop);
    }
    
    /**
     * Retrieves all components registered with this mediator
     * 
     * @return List of all registered components
     */
    public List<MediatorComponent> getAllComponents() {
        return new ArrayList<>(components.values());
    }
}