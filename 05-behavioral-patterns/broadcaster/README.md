# Broadcaster Application

A Spring Boot application demonstrating three behavioral design patterns through a command-line interface. Components communicate asynchronously, sending messages to each other using different patterns.

## Design Patterns Demonstrated

1. **Chain of Responsibility** - Messages pass through a chain of handlers
2. **Mediator** - Components communicate through a central mediator
3. **Observer** - Components subscribe to and receive notifications from subjects

## Quick Start

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- 100MB of free disk space for logs

### Run the Application

```bash
mvn spring-boot:run
```

## Available Commands

Once the application starts, you'll see a shell prompt (`shell:>`). Use these commands:

| Command | Description |
|---------|-------------|
| `start-all` | Start all pattern implementations |
| `start-pattern [pattern]` | Start a specific pattern (chain, mediator, or observer) |
| `app-info` | Display information about the application |
| `delete-logs` | Delete all log files |

Examples:
```bash
shell:> start-all
shell:> start-pattern chain
shell:> start-pattern mediator
shell:> start-pattern observer
shell:> app-info
shell:> delete-logs
```

## What Happens When Running

1. Components send messages at random intervals
2. Log files are created in the `logs` directory
3. The application automatically shuts down after 1 minute

## Viewing Logs

Each component writes to its own log file in the `logs` directory:

- Chain components: `logs\chain-*.log`
- Mediator components: `logs\mediator-*.log`
- Observer components: `logs\observer-*.log`

View all logs:
- Windows: `type logs\*.log`
- Linux/Mac: `cat logs/*.log`

## Running Tests

The application includes comprehensive unit tests for all components and patterns.

Run all tests:
```bash
mvn test
```

Run tests for a specific pattern:
```bash
# For Chain of Responsibility pattern
mvn test -Dtest=*Chain*Test

# For Mediator pattern
mvn test -Dtest=*Mediator*Test

# For Observer pattern
mvn test -Dtest=*Observer*Test
```

## Building the Application

Build a standalone JAR file:
```bash
mvn clean package
```

Run the JAR file:
```bash
java -jar target/broadcaster-1.0-SNAPSHOT.jar
```