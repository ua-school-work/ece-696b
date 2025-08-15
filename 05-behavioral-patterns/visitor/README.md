# Visitor Pattern for Exception Handling

This project demonstrates how to refactor complex exception handling using the Visitor design pattern for cleaner, more maintainable code.

### Before: The Problem

Handling numerous specific exceptions often leads to long, repetitive, and brittle `try-catch` blocks.
```
java // A long chain of catch blocks is hard to read and maintain. try { madMethod(); } catch (AuthorizationException e) { // ... specific handling ... } catch (BusinessRuleViolation e) { // ... specific handling ... } catch (DuplicateEntityViolation e) { // ... specific handling ... } catch (SearchException e) { // ... specific handling ... } // ... and so on.
``` 

### After: The Solution

The Visitor pattern simplifies this by delegating the handling logic to a single object. The client code becomes trivial.
```
java // The caller only needs to catch the base exception. try { madMethod(); } catch (ServiceException e) { // Delegate handling to the visitor. e.accept(new ExceptionHandler()); }
``` 

### How It Works

This approach uses **double dispatch**: the exception object "accepts" a visitor (the `ExceptionHandler`), which in turn executes the correct logic for that specific exception. This centralizes all handling logic in the visitor, decoupling it from the code where the error occurs.

### Key Benefits

*   **Cleaner Code:** Eliminates long, repetitive `catch` blocks.
*   **Centralized Logic:** All exception-handling behavior lives in one place.
*   **Extensible:** Adding a new exception type is easy and doesn't require changing the client code.

## Running the Demo

**Prerequisites:**
*   Java 21
*   Maven

**Instructions:**
1.  `mvn package`
2.  `java -cp target/classes edu.arizona.josesosa.patterns.behavioral.Main`

Each run will randomly throw and handle a different exception, or print "Yupee" on success.
