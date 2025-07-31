# Search Challenge

This project demonstrates a generic search framework with specific implementations for Person and Car objects. The framework provides a flexible way to search for objects based on various criteria and sort the results.

## Project Structure

- `GenericSearch<T>`: Abstract base class that provides generic search functionality
- `ISearch`: Interface defining the search contract
- `PersonSearch`: Implementation for searching Person objects
- `CarSearch`: Implementation for searching Car objects
- `SortCriteria`: Interface for defining sort criteria
- `BiMap`: Utility class for bidirectional mapping

## Prerequisites

- Java 21
- Maven

## How to Run the Code

1. Clone the repository
2. Build the project using Maven:
   ```
   mvn clean install
   ```
3. Run the application using Java:
   ```
   java -cp target/classes edu.arizona.josesosa.Main
   ```

Alternatively, you can run the `Main` class directly from your IDE, which is the recommended approach for development.

The `Main` class demonstrates how to use the search functionality:
- Creating a `PersonSearch` object, setting search criteria, and executing a search
- Creating a `CarSearch` object, setting search criteria, and executing a search

## How to Run the Tests

The project includes comprehensive unit and integration tests. To run all tests:

```
mvn test
```

To run a specific test class:

```
mvn test -Dtest=SearchIntegrationTest
```

To run a specific test method:

```
mvn test -Dtest=SearchIntegrationTest#testMainScenario
```

## Test-Driven Development

The unit tests for this project were created before refactoring the code to ensure that the refactoring did not break the existing functionality. This approach follows the principles of Test-Driven Development (TDD) and helps maintain code quality during refactoring.
