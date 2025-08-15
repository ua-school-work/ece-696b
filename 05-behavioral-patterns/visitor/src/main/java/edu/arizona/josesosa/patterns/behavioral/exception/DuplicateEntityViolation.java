package edu.arizona.josesosa.patterns.behavioral.exception;

import edu.arizona.josesosa.patterns.behavioral.INamedEntity;


public class DuplicateEntityViolation extends BusinessException implements Visitee {

    private static final long serialVersionUID = 1L;

    private INamedEntity duplicatedEntity;

    public DuplicateEntityViolation(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEntityViolation(String message) {
        super(message);
    }

    public DuplicateEntityViolation(String message, INamedEntity duplicatedEntity) {
        super(message);
        this.duplicatedEntity = duplicatedEntity;
    }

    public DuplicateEntityViolation(INamedEntity duplicatedEntity) {
        super("Duplicate");
        this.duplicatedEntity = duplicatedEntity;
    }

    public INamedEntity getDuplicatedEntity() {
        return duplicatedEntity;
    }

    public void setDuplicatedEntity(INamedEntity duplicatedEntity) {
        this.duplicatedEntity = duplicatedEntity;
    }

    @Override
    public void accept(ExceptionHandler handler) {
        handler.visit(this);
    }
}
