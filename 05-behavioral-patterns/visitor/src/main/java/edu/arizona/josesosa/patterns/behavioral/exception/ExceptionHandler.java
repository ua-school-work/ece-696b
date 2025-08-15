package edu.arizona.josesosa.patterns.behavioral.exception;

public class ExceptionHandler implements ServiceExceptionVisitor {

    @Override
    public void visit(AuthorizationException e) {
        System.err.println("From AuthorizationException visitor method:");
        System.err.println(e.getErrorName() + " " + e.getType());
    }

    @Override
    public void visit(BusinessRuleViolation e) {
        System.err.println("From BusinessRuleViolation visitor method:");
        System.err.println(e.getErrorName() + " " + e.getBusinessRuleViolated() + " " + e.getCauser().getClass().getSimpleName());
    }

    @Override
    public void visit(DuplicateEntityViolation e) {
        System.err.println("From DuplicateEntityViolation visitor method:");
        System.err.println(e.getErrorName() + " " + e.getDuplicatedEntity().getName());
    }

    @Override
    public void visit(SearchException e) {
        System.err.println("From SearchException visitor method:");
        System.err.println(e.getErrorName() + " " + e.getMessage());
    }

    @Override
    public void visit(ServiceException e) {
        System.err.println("From ServiceException visitor method:");
        System.err.println("Unexpected error" + e.getErrorName() + " " + e.getErrorName());
    }

    @Override
    public void visit(SystemError e) {
        System.err.println("From SystemError visitor method:");
        System.err.println("Unexpected error" + e.getErrorName() + " " + e.getType());
    }
}
