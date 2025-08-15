package edu.arizona.josesosa.patterns.behavioral.exception;

public interface Visitee {
    public abstract void accept(ExceptionHandler handler);
}
