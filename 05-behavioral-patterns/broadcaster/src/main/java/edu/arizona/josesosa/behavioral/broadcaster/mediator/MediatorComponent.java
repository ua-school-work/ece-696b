package edu.arizona.josesosa.behavioral.broadcaster.mediator;

import edu.arizona.josesosa.behavioral.broadcaster.common.BaseComponent;
import edu.arizona.josesosa.behavioral.broadcaster.common.Command;
import edu.arizona.josesosa.behavioral.broadcaster.common.Message;
import edu.arizona.josesosa.behavioral.broadcaster.common.MessageType;

/**
 * Component implementation for the Mediator pattern that communicates through a central mediator.
 */
public class MediatorComponent extends BaseComponent {
    
    private Mediator mediator;
    
    /**
     * Creates a new mediator component with the specified ID
     * 
     * @param id The unique identifier for this component
     */
    public MediatorComponent(String id) {
        super(id);
    }
    
    /**
     * Connects this component to a mediator and registers with it
     * 
     * @param mediator The mediator to use for communication
     */
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
        mediator.register(this);
    }
    
    @Override
    protected void deliverMessage(Message message) {
        if (mediator != null) {
            mediator.send(message);
        } else {
            logMediatorNotAvailable("deliver message");
        }
    }
    
    @Override
    protected void sendCommandToRandomRecipient(Command command) {
        if (mediator != null) {
            String recipientId = getRandomRecipientId();
            sendMessage(recipientId, MessageType.COMMAND, command);
        } else {
            logMediatorNotAvailable("send command");
        }
    }
    
    @Override
    protected void sendTextToRandomRecipient(String text) {
        if (mediator != null) {
            String recipientId = getRandomRecipientId();
            sendMessage(recipientId, MessageType.TEXT, text);
        } else {
            logMediatorNotAvailable("send text");
        }
    }
    
    /**
     * Gets a random recipient ID from the mediator
     * 
     * @return ID of a randomly selected component
     */
    private String getRandomRecipientId() {
        return mediator.getRandomComponentId(id);
    }
    
    /**
     * Logs that the mediator is not available for an operation
     * 
     * @param operation The operation that couldn't be performed
     */
    private void logMediatorNotAvailable(String operation) {
        logMessage("Cannot " + operation + ": no mediator set");
    }
}