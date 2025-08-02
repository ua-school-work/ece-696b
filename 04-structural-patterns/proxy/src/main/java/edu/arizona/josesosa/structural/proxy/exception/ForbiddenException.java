package edu.arizona.josesosa.structural.proxy.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException() {
        super("Censored");
    }
}
