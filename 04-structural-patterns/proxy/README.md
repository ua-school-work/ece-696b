# Proxy Homework

## Overview
This project demonstrates the implementation of the Proxy design pattern, one of the structural design patterns defined in the Gang of Four (GoF) design patterns. The Proxy pattern provides a surrogate or placeholder for another object to control access to it.

In this implementation, we use a dynamic proxy to intercept method calls to an interface. The proxy performs several functions:

1. **Access Control**: Blocks access to methods when arguments contain forbidden words (e.g., "secret")
2. **Logging**: Records method invocations, completions, and failures
3. **Performance Monitoring**: Measures and reports the execution time of method calls

## Implementation Details
The project consists of the following key components:

### 1. Interface and Implementation
- `MyInterface`: Defines the methods that will be proxied
- `MyInterfaceImpl`: Provides the actual implementation of the interface

### 2. Proxy Implementation
- `ProxyClass`: Implements `InvocationHandler` to intercept method calls
  - Checks for forbidden arguments
  - Logs method invocations
  - Measures execution time
  - Forwards calls to the actual implementation

### 3. Exception Handling
- `ForbiddenException`: Custom exception thrown when forbidden arguments are detected

### 4. Application
- `Application`: Demonstrates the proxy pattern with various test cases

## How to Run the Demo
To run the demonstration application:
```bash
# Navigate to the project directory
cd 04-structural-patterns/proxy

# Build the project
mvn clean package

# Run the application
java -cp target/classes edu.arizona.josesosa.structural.proxy.Application
```

The application will demonstrate:
- Method calls with no arguments
- Method calls with allowed arguments
- Method calls with forbidden arguments (containing "secret")
- How the proxy intercepts and handles each case

## How to Run the Unit Tests
The project includes unit tests for all components. To run the tests:
```bash
# Navigate to the project directory
cd 04-structural-patterns/proxy

# Run the tests
mvn test
```
The tests verify:
- The functionality of the `ForbiddenException`
- The behavior of the `ProxyClass`
- The implementation of `MyInterfaceImpl`

## Key Concepts Demonstrated
1. **Dynamic Proxy**: Using Java's `Proxy.newProxyInstance()` to create a proxy at runtime
2. **Reflection**: Using Java's reflection API to invoke methods on the target object
3. **Separation of Concerns**: The proxy handles cross-cutting concerns like logging and access control
4. **Open/Closed Principle**: Adding new functionality without modifying the original implementation