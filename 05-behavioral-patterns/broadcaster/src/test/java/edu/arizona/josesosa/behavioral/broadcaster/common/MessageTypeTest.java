package edu.arizona.josesosa.behavioral.broadcaster.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the MessageType enum.
 */
public class MessageTypeTest {

    @Test
    public void testEnumValues() {
        // Verify that the enum has exactly two values
        assertThat(MessageType.values().length).isEqualTo(2);
        
        // Verify that the enum contains TEXT and COMMAND values
        assertThat(MessageType.valueOf("TEXT")).isEqualTo(MessageType.TEXT);
        assertThat(MessageType.valueOf("COMMAND")).isEqualTo(MessageType.COMMAND);
    }
    
    @Test
    public void testEnumOrdinals() {
        // Verify the ordinal values of the enum constants
        assertThat(MessageType.TEXT.ordinal()).isEqualTo(0);
        assertThat(MessageType.COMMAND.ordinal()).isEqualTo(1);
    }
}