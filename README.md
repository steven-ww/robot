# Robot Wars Game Client

A Java client application for participating in Robot Wars battles by interacting with the Robot Wars API.

## Overview

This project represents a robot that will play a game of robot wars by calling APIs. The application provides a command-line interface to:

- Create new battles
- Register robots to battles
- Start battles
- Monitor battle and robot status
- Control robot actions during gameplay

## Features

### Core Functionality
- **Battle Management**: Create new battles or join existing ones
- **Robot Registration**: Register robots with custom names to battles
- **Battle Control**: Start battles when ready
- **Status Monitoring**: Check battle and robot status in real-time
- **Robot Actions**: Move, scan radar, and fire lasers during gameplay

### Game Rules
- A battle needs at least two robots to start
- Robots can move around the arena
- Robots can use radar to scan surroundings
- Robots can fire lasers to hit other robots
- Robot state is synchronized with the server (crashes, hits, destruction, hit points)

## Tech Stack

- **Java 21**: Latest Java features and language constructs
- **Gradle**: Build automation and dependency management
- **JUnit 5**: Unit testing framework
- **Mockito**: Mocking framework for unit testing
- **PMD**: Static code analysis tool
- **Java Records**: Modern data modeling
- **Java HTTP Client**: Built-in HTTP client for API communication
- **Jackson**: JSON processing and serialization
- **JSR-310**: Java time API support

## Project Structure

```
app/
├── src/
│   ├── main/java/za/co/sww/rwars/robot/
│   │   ├── App.java                    # Main application with CLI menu
│   │   ├── client/
│   │   │   ├── RobotWarsApiClient.java # API client interface
│   │   │   ├── RobotWarsApiClientImpl.java # HTTP API client implementation
│   │   │   └── RobotWarsApiException.java # Custom exception class
│   │   ├── model/
│   │   │   ├── Battle.java             # Battle domain model
│   │   │   ├── Robot.java              # Robot domain model
│   │   │   └── Position.java           # Position coordinates
│   │   └── service/
│   │       └── RobotWarsService.java   # Business logic layer
│   └── test/java/za/co/sww/rwars/robot/
│       ├── client/
│       ├── model/
│       └── service/
└── build.gradle
```

## API Documentation

The Robot Wars API documentation is available at: https://api.rwars.steven-webber.com/swagger-ui/

## Getting Started

### Prerequisites

- Java 21 or higher
- Gradle 7.x or higher

### Building the Project

```bash
# Clone the repository
git clone <repository-url>
cd robot

# Build the project
./gradlew build

# Run tests
./gradlew test
```

### Running the Application

**Option 1: Use the convenience script (Recommended)**
```bash
./run-robot-wars.sh
```

**Option 2: Run the JAR directly**
```bash
# Build first (if not already built)
./gradlew build

# Run the JAR
java -jar app/build/libs/app.jar
```

**Option 3: Use Gradle with plain console**
```bash
./gradlew run --console=plain
```

### Application Menu

The application provides a command-line menu with the following options:

1. **Create Battle**: Creates a new battle and returns a battle ID
2. **Register Robot**: Registers a robot with a given name to a battle
3. **Start Battle**: Starts a battle (requires at least 2 robots)
4. **Get Battle Status**: Shows current battle information
5. **Get Robot Status**: Shows detailed robot information

### Example Usage

```
Robot Wars Game Client
======================

=== Robot Wars Menu ===
1. Create Battle
2. Register Robot
3. Start Battle
4. Get Battle Status
5. Get Robot Status
0. Exit

Choose an option: 1

Creating new battle...
Battle created successfully!
Battle ID: abc123
Status: WAITING
Created at: 2024-01-01T10:00:00Z
```

## Development

### Code Standards

- Always create unit tests for any changes
- Use the latest Java 21 compatible language features
- Ensure code is clean and follows coding standards
- Run the complete build to verify all changes
- Handle exceptions gracefully and log errors appropriately
- Keep documentation up to date

### Testing

```bash
# Run all tests
./gradlew test

# Run tests with coverage
./gradlew test jacocoTestReport

# Run specific test class
./gradlew test --tests "za.co.sww.rwars.robot.model.BattleTest"
```

### Code Quality

```bash
# Run PMD static analysis
./gradlew pmdMain pmdTest

# Run full build including tests and PMD
./gradlew build

# Check PMD reports
open app/build/reports/pmd/main.html
open app/build/reports/pmd/test.html
```

### Contributing

1. Create unit tests for new functionality
2. Follow existing code patterns and conventions
3. Update documentation as needed
4. Ensure all tests pass before submitting
5. Handle exceptions appropriately

## Implementation Status

### Completed ✅
- Domain models (Battle, Robot, Position)
- Service layer architecture
- API client interface definition
- **HTTP API client implementation**
- Command-line interface
- Exception handling framework
- Project structure and build configuration
- **Integration with Robot Wars API**
- Comprehensive unit test coverage
- Jackson JSON processing
- Java 21 HTTP client integration
- **PMD static code analysis**
- Code quality checks and standards

### Pending 🚧
- Robot movement and combat logic
- Configuration management
- Logging framework integration
- More comprehensive error handling
- Integration tests with live API
- Authentication handling (if required)

## Game Flow

1. **Setup Phase**:
   - Create a new battle OR join existing battle with ID
   - Register robot with custom name
   - Wait for other robots to join

2. **Battle Phase**:
   - Start battle when ready (minimum 2 robots)
   - Monitor robot status and battle state
   - Execute robot actions (move, scan, fire)

3. **End Phase**:
   - Battle ends when only one robot remains
   - View final battle results and winner

## Error Handling

The application includes comprehensive error handling:

- **Network Errors**: API connection failures
- **Validation Errors**: Invalid input parameters
- **Game State Errors**: Invalid battle/robot states
- **Authentication Errors**: API access issues

All errors are logged appropriately and user-friendly messages are displayed.

## Future Enhancements

- Automated robot behavior (AI strategies)
- Battle replay and analysis
- Multi-battle tournament support
- Real-time battle visualization
- Configuration file support
- Performance metrics and statistics

## License

This project is for educational and demonstration purposes.

## Contact

For questions or issues, please refer to the project documentation or contact the development team.
