package edu.arizona.josesosa.patterns.behavioral.exception;


public class SearchException extends ServiceException implements Visitee {

    private static final long serialVersionUID = 1L;

    public SearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchException(String message) {
        super(message);
    }

    @Override
    public void accept(ExceptionHandler handler) {
        handler.visit(this);
    }

}
