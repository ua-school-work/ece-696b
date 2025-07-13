# KWIC Architectural Implementations

This repository contains four implementations of the Key Word in Context (KWIC) system, each in its own Maven project folder.

## Prerequisites
*   Java 21
*   Apache Maven

## How to Run a Project

1.  **Navigate** to one of the project directories:
    *   `abstract-data-type`
    *   `shared-data`
    *   `pipes-and-filters`
    *   `implicit-invocation`

    For example:
    ```bash
    cd shared-data
    ```

2.  **Compile and Run** the project using Maven:
    ```bash
    mvn compile exec:java
    ```

The program will read the pre-included `input.txt` file from the `src/main/resources` directory and generate a corresponding output file in the same location. You can modify the `input.txt` file to test more complex scenarios.