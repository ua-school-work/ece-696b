package edu.arizona.josesosa.behavioral.broadcaster.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the Command interface.
 */
public class CommandTest {

    @Test
    public void testCommandExecution() {
        // Arrange
        String expectedResult = "Command executed successfully";
        Command command = () -> expectedResult;

        // Act
        String result = command.execute();

        // Assert
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCommandExecutionWithException() {
        // Arrange
        Command command = () -> {
            throw new RuntimeException("Command execution failed");
        };

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, command::execute);
        assertThat(exception.getMessage()).isEqualTo("Command execution failed");
    }

    @Test
    public void testCommandAsLambda() {
        // Arrange
        Command command = () -> "Result from lambda";

        // Act
        String result = command.execute();

        // Assert
        assertThat(result).isEqualTo("Result from lambda");
    }

    @Test
    public void testCommandAsAnonymousClass() {
        // Arrange
        Command command = new Command() {
            @Override
            public String execute() {
                return "Result from anonymous class";
            }
        };

        // Act
        String result = command.execute();

        // Assert
        assertThat(result).isEqualTo("Result from anonymous class");
    }
}