package edu.arizona.josesosa.patterns.behavioral.exception;

public interface ServiceExceptionVisitor {
    void visit(AuthorizationException e);
    void visit(BusinessRuleViolation e);
    void visit(DuplicateEntityViolation e);
    void visit(SearchException e);
    void visit(SystemError e);
    void visit(ServiceException e);
}