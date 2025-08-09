package edu.arizona.josesosa.behavioral.broadcaster.common;

/**
 * Interface for executable commands.
 * Commands can be sent as payloads in COMMAND type messages.
 */
public interface Command {
    
    /**
     * Execute the command and return the result
     * 
     * @return The result of the command execution
     */
    String execute();
}