package edu.arizona.josesosa.behavioral.broadcaster.common;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a message that can be sent between components.
 * A message contains:
 * - A unique identifier
 * - Sender identifier
 * - Recipient identifier ('*' means broadcast to all)
 * - Message type (TEXT or COMMAND)
 * - Payload (content of the message)
 * - Timestamp when the message was created
 */
@Data
@Builder
public class Message {
    /**
     * Unique identifier for the message
     */
    private final String id;
    
    /**
     * Identifier of the component that sent the message
     */
    private final String senderId;
    
    /**
     * Identifier of the intended recipient
     * '*' means broadcast to all components
     */
    private final String recipientId;
    
    /**
     * Type of the message (TEXT or COMMAND)
     */
    private final MessageType type;
    
    /**
     * Content of the message
     */
    private final Object payload;
    
    /**
     * Timestamp when the message was created
     */
    private final LocalDateTime timestamp;
    
    /**
     * Factory method to create a new message
     * 
     * @param senderId Identifier of the sender
     * @param recipientId Identifier of the recipient ('*' for broadcast)
     * @param type Type of the message
     * @param payload Content of the message
     * @return A new Message instance
     */
    public static Message create(String senderId, String recipientId, MessageType type, Object payload) {
        return Message.builder()
                .id(UUID.randomUUID().toString())
                .senderId(senderId)
                .recipientId(recipientId)
                .type(type)
                .payload(payload)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    /**
     * Check if this message is a broadcast message
     * 
     * @return true if the message is a broadcast, false otherwise
     */
    public boolean isBroadcast() {
        return "*".equals(recipientId);
    }
    
    /**
     * Check if this message is intended for a specific recipient
     * 
     * @param componentId The component ID to check
     * @return true if the message is for the specified component, false otherwise
     */
    public boolean isForRecipient(String componentId) {
        return recipientId.equals(componentId) || isBroadcast();
    }
    
    /**
     * Check if this message is a text message
     * 
     * @return true if the message is a text message, false otherwise
     */
    public boolean isTextMessage() {
        return type == MessageType.TEXT;
    }
    
    /**
     * Check if this message is a command message
     * 
     * @return true if the message is a command message, false otherwise
     */
    public boolean isCommandMessage() {
        return type == MessageType.COMMAND;
    }
    
    /**
     * Get the payload as a string
     * 
     * @return The payload as a string
     */
    public String getPayloadAsString() {
        return payload != null ? payload.toString() : "";
    }
}