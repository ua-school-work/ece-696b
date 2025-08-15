package edu.arizona.josesosa.patterns.behavioral;

import edu.arizona.josesosa.patterns.behavioral.exception.*;


public class Main {


    public static void madMethod() throws AuthorizationException, BusinessRuleViolation, DuplicateEntityViolation, SearchException, SystemError {
        double seed = Math.random();
        if (seed < 0.15) {
            throw new AuthorizationException(AuthorizationException.Type.ACCESS_DENIED, new Exception("User crab not allowed"));
        } else if (seed < 0.3) {
            throw new DuplicateEntityViolation("Duplicate issue", new NamedEntity());
        } else if (seed < 0.45) {
            throw new BusinessRuleViolation("Holy crab", BusinessRuleViolation.BusinessRule.FEE_TOO_SMALL, new NamedEntity());
        } else if (seed < 0.6) {
            throw new SearchException("Holy moly query");
        } else if (seed < 0.75) {
            throw new SystemError("Gosh", new Exception("Crab error"), edu.arizona.josesosa.patterns.behavioral.exception.SystemError.Type.TRANSIENT_INSTANCE);
        }
        System.out.println("Yupee");
    }
    
    /**
     * Main method to demonstrate the Visitor pattern for handling exceptions.
     * It simulates a method that can throw various exceptions, which are then handled
     * by the ExceptionHandler using the Visitor pattern.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            madMethod();
        } catch (ServiceException e) {
            var handler = new ExceptionHandler();
            e.accept(handler);
        }
    }
}
