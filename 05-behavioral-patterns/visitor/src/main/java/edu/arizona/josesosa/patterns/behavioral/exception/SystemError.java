package edu.arizona.josesosa.patterns.behavioral.exception;


public class SystemError extends ServiceException implements Visitee {

    private static final long serialVersionUID = 1L;

    public enum Type {
        PARSING_ERROR, INVALID_REFERENCE, TRANSIENT_INSTANCE,
        UNSPECIFIED
    }

    ;

    private Type type = Type.UNSPECIFIED;

    public SystemError(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemError(String message) {
        super(message);
    }

    public SystemError(String message, Throwable cause,
                       Type type) {
        super(message, cause);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type errorType) {
        this.type = errorType;
    }

    @Override
    public String getErrorName() {
        return super.getErrorName() + "." + type;
    }

    @Override
    public void accept(ExceptionHandler handler) {
        handler.visit(this);
    }
}
