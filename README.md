# Advanced Object-Oriented Development

---
## Assignment 1: KWIC Design Patterns
The `kwic-assignment` directory contains four different architectural implementations of the Key Word in Context (KWIC) system.

For detailed instructions on how to build and run each of the KWIC projects, please see the [KWIC Assignment README](01-kwic-assignment/README.md).

---

## Assignment 2: Refactoring SearchingCore
The `refactoring-searching-core` directory contains a refactored version of the SearchingCore project, which is a part
of the Searching Challenge. The refactoring focuses on improving the design and structure of the codebase while maintaining
its functionality.

To learn more about the project, please see the [search-challenge README](02-refactoring-searching-core/search-challenge/README.md). `search-challenge`

___
## Assignment 3: Creational Patterns - Singleton

This project demonstrates the Singleton design pattern, which ensures that a class has only one instance and provides a
global point of access to it. For more details on running the project and the tests,
please see the [Singleton README](03-creational-patterns/singleton/README.md).

---
## Assignment 4: Creational Patterns - Factory

This project demonstrates the Factory design pattern, which provides an interface for creating objects without specifying their concrete classes.
The implementation includes multiple design patterns such as Factory, Strategy, Adapter, Fluent Interface, and Template Method.
Key features include UPS delivery service integration, an Amazon store with exclusive UPS support, price reduction through an adapter, and strategy-based delivery selection.
For more details on running the project and the tests, please see the [Factory README](03-creational-patterns/factory/README.md).

This project also includes an implementation of the Abstract Factory pattern, which is an extension of the Factory Method pattern. The Abstract Factory implementation
includes features such as an abstract `AbstractFactory` class with methods for creating products, stores, and distributors, concrete factories for Amazon, Ebay, and
Walmart, and application of the Flyweight pattern for caching products. The Abstract Factory pattern provides benefits like enforced compatibility, reduced memory
usage, simplified client code, and centralized configuration.
For more details on the Abstract Factory implementation, please see the [Abstract Factory README](03-creational-patterns/abstract-factory/README.md).

---
## Assignment 5: Structural Patterns - Adapter

This project demonstrates the Adapter design pattern, which allows objects with incompatible interfaces to collaborate.
For more details on running the project and the tests, please see the [Adapter README](04-structural-patterns/adapter/README.md).

---
## Assignment 6: Structural Patterns - Bridge

This project demonstrates the Bridge design pattern, which is meant to decouple an abstraction from its implementation
so that the two can vary independently. For more details on running the project and the tests,
please see the [Bridge README](04-structural-patterns/bridge/README.md).

---
## Assignment 7: Structural Patterns - Proxy

This project demonstrates the Proxy design pattern, which provides a surrogate or placeholder for another object to control access to it.
The implementation uses a dynamic proxy to intercept method calls for access control, logging, and performance monitoring.
For more details on running the project and the tests, please see the [Proxy README](04-structural-patterns/proxy/README.md).

---
## Assignment 8: Structural Patterns - Decorator

This project demonstrates the Decorator design pattern, which allows behavior to be added to individual objects, either statically or dynamically, without affecting the behavior of other objects from the same class.
The implementation shows how multiple decorators can be stacked to add combined functionality (compression and encryption) to a base component (file data source).
For more details on running the project and the tests, please see the [Decorator README](04-structural-patterns/decorator/README.md).

---
## Assignment 9: Behavioral Patterns - Broadcaster

This project demonstrates three behavioral design patterns through a Spring Boot command-line application:
1. **Chain of Responsibility** - Messages pass through a chain of handlers
2. **Mediator** - Components communicate through a central mediator
3. **Observer** - Components subscribe to and receive notifications from subjects

The application allows components to communicate asynchronously by sending messages to each other using these different patterns. It includes features like automatic log generation and comprehensive unit tests for all components.
For more details on running the application and the tests, please see the [Broadcaster README](05-behavioral-patterns/broadcaster/README.md).

---
## Assignment 10: Behavioral Patterns - Visitor

This project demonstrates the Visitor pattern applied to exception handling to eliminate long chains of catch blocks. By using double dispatch, exceptions accept a visitor (an ExceptionHandler) that centralizes handling logic, resulting in cleaner, more maintainable, and easily extensible code.

For more details on running the project and the tests, please see the [Visitor README](05-behavioral-patterns/visitor/README.md).
