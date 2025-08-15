package edu.arizona.josesosa.patterns.behavioral.exception;


public abstract class ServiceException extends Exception implements Visitee {

    private static final long serialVersionUID = 1L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorName() {
        return this.getClass().getName();
    }

    public void accept(ExceptionHandler handler) {
        handler.visit(this);
    }
}
