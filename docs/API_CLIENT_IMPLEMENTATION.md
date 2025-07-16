# Robot Wars API Client Implementation

## Overview

This document describes the implementation of the Robot Wars API client (`RobotWarsApiClientImpl`) that integrates with the Robot Wars API at `https://api.rwars.steven-webber.com`.

## Architecture

The API client follows a layered architecture:

```
App.java
    ↓
RobotWarsService.java
    ↓  
RobotWarsApiClient (interface)
    ↓
RobotWarsApiClientImpl (implementation)
    ↓
HTTP Client + Jackson JSON
    ↓
Robot Wars API
```

## Key Components

### 1. RobotWarsApiClientImpl

The main implementation class that handles HTTP communication with the Robot Wars API.

**Key Features:**
- Uses Java 21's built-in HTTP client
- JSON serialization/deserialization with Jackson
- Proper error handling and exception mapping
- Support for all API endpoints defined in the interface

**Dependencies:**
- `java.net.http.HttpClient` - HTTP communication
- `com.fasterxml.jackson.databind.ObjectMapper` - JSON processing
- `com.fasterxml.jackson.datatype.jsr310.JavaTimeModule` - Java 8+ time support

### 2. HTTP Methods

The client implements both GET and POST HTTP methods:

- **GET requests**: For retrieving battle and robot state
- **POST requests**: For creating battles, registering robots, and robot actions

### 3. Error Handling

The implementation includes comprehensive error handling:

- **Network errors**: Connection failures, timeouts
- **HTTP errors**: Non-2xx status codes
- **JSON processing errors**: Malformed responses
- **Custom exceptions**: `RobotWarsApiException` with status codes

## API Endpoints

The client implements the following endpoints:

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/battles` | Create a new battle |
| GET | `/battles/{id}` | Get battle information |
| POST | `/battles/{id}/robots` | Register a robot |
| POST | `/battles/{id}/start` | Start a battle |
| GET | `/robots/{id}` | Get robot state |
| POST | `/robots/{id}/move` | Move robot |
| POST | `/robots/{id}/radar` | Scan with radar |
| POST | `/robots/{id}/fire` | Fire laser |

## Request/Response Models

### Request DTOs

The client uses internal record classes for request payloads:

```java
private record RobotRequest(String name) {}
private record MoveRequest(int direction, int distance) {}
private record ScanRequest(int direction, int range) {}
private record FireRequest(int direction, int range) {}
```

### Response Models

Responses are mapped to domain models:
- `Battle` - Battle information
- `Robot` - Robot state and information
- `List<Robot>` - For radar scan results
- `Boolean` - For laser fire results

## Configuration

The client is configured with:
- **Base URL**: `https://api.rwars.steven-webber.com`
- **Content Type**: `application/json`
- **Jackson Modules**: JSR-310 time module for proper date/time handling

## Error Handling Strategy

1. **HTTP Status Codes**: 
   - 200-299: Success, parse response
   - Other codes: Throw `RobotWarsApiException` with status code

2. **JSON Processing**:
   - Parsing errors throw `RobotWarsApiException`
   - Malformed JSON is handled gracefully

3. **Network Issues**:
   - Connection timeouts
   - DNS resolution failures
   - SSL/TLS errors

## Testing

The implementation includes comprehensive unit tests:

- **Unit Tests**: Basic functionality and error handling
- **Integration Tests**: With mock HTTP responses (future enhancement)
- **API Tests**: With live API (future enhancement)

## Future Enhancements

1. **Retry Logic**: Implement exponential backoff for failed requests
2. **Caching**: Cache battle and robot state for better performance
3. **Authentication**: Add API key or token support if required
4. **Metrics**: Add request/response timing and success rate monitoring
5. **Circuit Breaker**: Implement circuit breaker pattern for resilience
6. **Connection Pooling**: Optimize HTTP client configuration
7. **Request/Response Logging**: Add detailed logging for debugging

## Usage Example

```java
// Create client
RobotWarsApiClient client = new RobotWarsApiClientImpl();

// Create battle
Battle battle = client.createBattle();
System.out.println("Battle ID: " + battle.battleId());

// Register robot
Robot robot = client.registerRobot(battle.battleId(), "MyBot");
System.out.println("Robot ID: " + robot.robotId());

// Start battle
client.startBattle(battle.battleId());

// Move robot
client.moveRobot(robot.robotId(), 90, 10);

// Fire laser
boolean hit = client.fireLaser(robot.robotId(), 0, 100);
```

## Error Handling Example

```java
try {
    Battle battle = client.createBattle();
    // Handle success
} catch (RobotWarsApiException e) {
    System.err.println("API Error: " + e.getMessage());
    if (e.getStatusCode() > 0) {
        System.err.println("HTTP Status: " + e.getStatusCode());
    }
    // Handle error appropriately
}
```

## Thread Safety

The `RobotWarsApiClientImpl` is thread-safe:
- `HttpClient` is thread-safe
- `ObjectMapper` is thread-safe after configuration
- No mutable state is shared between requests

## Performance Considerations

- **HTTP/2**: Java's HTTP client supports HTTP/2 by default
- **Connection Reuse**: The client reuses connections automatically
- **Memory**: Efficient streaming for large responses
- **JSON Processing**: Jackson provides high-performance JSON processing

## Compliance

The implementation follows the guidelines:
- ✅ Java 21 compatible features
- ✅ Clean code standards
- ✅ Comprehensive unit tests
- ✅ Exception handling
- ✅ Updated documentation
