package edu.arizona.josesosa.behavioral.broadcaster.chain;

import edu.arizona.josesosa.behavioral.broadcaster.common.BaseComponent;
import edu.arizona.josesosa.behavioral.broadcaster.common.Command;
import edu.arizona.josesosa.behavioral.broadcaster.common.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the Chain of Responsibility pattern.
 * Each component in the chain can either handle a message or pass it to the next component.
 */
@Setter
@Getter
public class ChainComponent extends BaseComponent {

    private static final Random RANDOM = new Random();
    
    private ChainComponent nextInChain;
    private static List<ChainComponent> componentRegistry = new ArrayList<>();
    
    /**
     * Registers all components for random recipient selection
     * 
     * @param allComponents List of all components in the chain
     */
    public void setAllComponents(List<ChainComponent> allComponents) {
        componentRegistry = new ArrayList<>(allComponents);
    }
    
    /**
     * Creates a new chain component with the specified ID
     * 
     * @param id The unique identifier for this component
     */
    public ChainComponent(String id) {
        super(id);
    }

    @Override
    protected void deliverMessage(Message message) {
        if (message.isBroadcast()) {
            handleBroadcastMessage(message);
        } else if (message.isForRecipient(getId())) {
            receiveMessage(message);
        } else if (nextInChain != null) {
            forwardMessageToNextInChain(message);
        } else {
            logUndeliverableMessage(message);
        }
    }
    
    /**
     * Handles broadcast messages by processing them and forwarding to next component
     */
    private void handleBroadcastMessage(Message message) {
        receiveMessage(message);
        
        if (nextInChain != null) {
            logMessage(String.format("Forwarding broadcast message from %s to next component in chain", 
                    message.getSenderId()));
            nextInChain.deliverMessage(message);
        }
    }
    
    /**
     * Forwards a message to the next component in the chain
     */
    private void forwardMessageToNextInChain(Message message) {
        logMessage(String.format("Forwarding message from %s to next component in chain", 
                message.getSenderId()));
        nextInChain.deliverMessage(message);
    }
    
    /**
     * Logs that a message could not be delivered (reached end of chain)
     */
    private void logUndeliverableMessage(Message message) {
        logMessage(String.format("Message from %s could not be delivered to %s (end of chain)", 
                message.getSenderId(), message.getRecipientId()));
    }
    
    @Override
    protected void sendCommandToRandomRecipient(Command command) {
        String recipientId = selectRandomRecipientId();
        sendMessage(recipientId, edu.arizona.josesosa.behavioral.broadcaster.common.MessageType.COMMAND, command);
    }
    
    @Override
    protected void sendTextToRandomRecipient(String text) {
        String recipientId = selectRandomRecipientId();
        sendMessage(recipientId, edu.arizona.josesosa.behavioral.broadcaster.common.MessageType.TEXT, text);
    }
    
    /**
     * Selects a random recipient ID from available components
     * 
     * @return ID of the selected recipient, or this component's ID if no others available
     */
    private String selectRandomRecipientId() {
        if (!componentRegistry.isEmpty()) {
            List<ChainComponent> otherComponents = componentRegistry.stream()
                    .filter(c -> !c.getId().equals(getId()))
                    .toList();
            
            if (!otherComponents.isEmpty()) {
                ChainComponent randomRecipient = otherComponents.get(RANDOM.nextInt(otherComponents.size()));
                return randomRecipient.getId();
            }
        }
        
        return getId();
    }
}