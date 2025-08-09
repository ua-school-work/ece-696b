package edu.arizona.josesosa.behavioral.broadcaster.common;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the Message class.
 */
public class MessageTest {

    @Test
    public void testCreateMessage() {
        // Arrange
        String senderId = "sender-1";
        String recipientId = "recipient-1";
        MessageType type = MessageType.TEXT;
        String payload = "Test message";

        // Act
        Message message = Message.create(senderId, recipientId, type, payload);

        // Assert
        assertThat(message).isNotNull();
        assertThat(message.getId()).isNotNull();
        assertThat(message.getSenderId()).isEqualTo(senderId);
        assertThat(message.getRecipientId()).isEqualTo(recipientId);
        assertThat(message.getType()).isEqualTo(type);
        assertThat(message.getPayload()).isEqualTo(payload);
        assertThat(message.getTimestamp()).isNotNull();
        assertThat(message.getTimestamp()).isBefore(LocalDateTime.now().plusSeconds(1));
        assertThat(message.getTimestamp()).isAfter(LocalDateTime.now().minusMinutes(1));
    }

    @Test
    public void testIsBroadcast() {
        // Arrange
        Message broadcastMessage = Message.create("sender-1", "*", MessageType.TEXT, "Broadcast message");
        Message directMessage = Message.create("sender-1", "recipient-1", MessageType.TEXT, "Direct message");

        // Act & Assert
        assertThat(broadcastMessage.isBroadcast()).isTrue();
        assertThat(directMessage.isBroadcast()).isFalse();
    }

    @Test
    public void testIsForRecipient() {
        // Arrange
        String recipientId = "recipient-1";
        Message directMessage = Message.create("sender-1", recipientId, MessageType.TEXT, "Direct message");
        Message broadcastMessage = Message.create("sender-1", "*", MessageType.TEXT, "Broadcast message");
        Message otherMessage = Message.create("sender-1", "other-recipient", MessageType.TEXT, "Other message");

        // Act & Assert
        assertThat(directMessage.isForRecipient(recipientId)).isTrue();
        assertThat(broadcastMessage.isForRecipient(recipientId)).isTrue(); // Broadcast is for everyone
        assertThat(otherMessage.isForRecipient(recipientId)).isFalse();
    }

    @Test
    public void testIsTextMessage() {
        // Arrange
        Message textMessage = Message.create("sender-1", "recipient-1", MessageType.TEXT, "Text message");
        Message commandMessage = Message.create("sender-1", "recipient-1", MessageType.COMMAND, 
                (Command) () -> "Command executed");

        // Act & Assert
        assertThat(textMessage.isTextMessage()).isTrue();
        assertThat(commandMessage.isTextMessage()).isFalse();
    }

    @Test
    public void testIsCommandMessage() {
        // Arrange
        Message textMessage = Message.create("sender-1", "recipient-1", MessageType.TEXT, "Text message");
        Message commandMessage = Message.create("sender-1", "recipient-1", MessageType.COMMAND, 
                (Command) () -> "Command executed");

        // Act & Assert
        assertThat(textMessage.isCommandMessage()).isFalse();
        assertThat(commandMessage.isCommandMessage()).isTrue();
    }

    @Test
    public void testGetPayloadAsString() {
        // Arrange
        String textPayload = "Text message";
        Message textMessage = Message.create("sender-1", "recipient-1", MessageType.TEXT, textPayload);
        
        Command commandPayload = () -> "Command executed";
        Message commandMessage = Message.create("sender-1", "recipient-1", MessageType.COMMAND, commandPayload);
        
        Message nullPayloadMessage = Message.builder()
                .id("test-id")
                .senderId("sender-1")
                .recipientId("recipient-1")
                .type(MessageType.TEXT)
                .payload(null)
                .timestamp(LocalDateTime.now())
                .build();

        // Act & Assert
        assertThat(textMessage.getPayloadAsString()).isEqualTo(textPayload);
        assertThat(commandMessage.getPayloadAsString()).isEqualTo(commandPayload.toString());
        assertThat(nullPayloadMessage.getPayloadAsString()).isEmpty();
    }
}