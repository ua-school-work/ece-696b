# Singleton Pattern Example

This project demonstrates the Singleton design pattern. The Singleton pattern ensures that a class has only one instance and provides a global point of access to it.

## Project Structure

The project follows a standard Maven layout:

-   `src/main/java`: Contains the source code for the application, including the Singleton pattern implementation.
-   `src/test/java`: Contains the unit tests for the project.
-   `pom.xml`: The Project Object Model file for Maven, which contains project and configuration information.

## Prerequisites

-   Java 21
-   Maven

## How to Run the Code

1.  Clone the repository.
2.  Build the project using Maven:
    ```shell
    mvn clean install
    ```
3.  Run the application using Java:
    ```shell
    java -cp target/classes edu.arizona.josesosa.Main
    ```

You can also run the `Main` class directly from your IDE.

## How to Run the Tests

The project includes unit tests to verify the functionality of the Singleton implementation.

To run all tests, execute the following command:

```shell
mvn test
```

To run a specific test class:

```shell
mvn test -Dtest=SingletonTest
```

To run a specific test method:

```shell
mvn test -Dtest=SingletonTest#testSomeFunctionality
```
