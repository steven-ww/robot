package za.co.sww.rwars.robot.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotWarsApiClientImplTest {

    private RobotWarsApiClientImpl apiClient;

    @BeforeEach
    void setUp() {
        apiClient = new RobotWarsApiClientImpl();
    }

    @Test
    void apiClientCanBeCreated() {
        assertNotNull(apiClient);
    }

    @Test
    void apiClientImplementsInterface() {
        assertTrue(apiClient instanceof RobotWarsApiClient);
    }

    // Note: Network-dependent tests have been removed to ensure test reliability.
    // Integration tests with actual API calls should be in a separate test suite
    // or use mocked HTTP clients for proper unit testing.
}
