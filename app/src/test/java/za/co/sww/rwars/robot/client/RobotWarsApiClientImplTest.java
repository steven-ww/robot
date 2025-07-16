package za.co.sww.rwars.robot.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import za.co.sww.rwars.robot.model.CreateBattleRequest;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RobotWarsApiClientImplTest {

    private RobotWarsApiClientImpl apiClient;
    private HttpClient mockHttpClient;
    private HttpResponse<String> mockResponse;

    @BeforeEach
    void setUp() {
        mockHttpClient = Mockito.mock(HttpClient.class);
        mockResponse = Mockito.mock(HttpResponse.class);
        apiClient = new RobotWarsApiClientImpl(mockHttpClient);
    }

    @Test
    void apiClientCanBeCreated() {
        assertNotNull(apiClient);
    }

    @Test
    void apiClientImplementsInterface() {
        assertTrue(apiClient instanceof RobotWarsApiClient);
    }

    @Test
    void testCreateBattle() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"id\":\"battle123\", \"status\": \"READY\"}");

        var request = new CreateBattleRequest("Test Battle", 10, 10, 5.0);
        var battle = apiClient.createBattle(request);

        assertNotNull(battle);
        assertEquals("battle123", battle.battleId());
    }

    @Test
    void testRegisterRobot() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"id\":\"robot456\", \"name\": \"TestBot\"}");

        var robot = apiClient.registerRobot("battle123", "TestBot");

        assertNotNull(robot);
        assertEquals("robot456", robot.robotId());
        assertEquals("TestBot", robot.name());
    }

    @Test
    void testGetBattle() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"id\":\"battle123\", \"status\": \"READY\", \"name\": \"Test Battle\"}");

        var battle = apiClient.getBattle("battle123");

        assertNotNull(battle);
        assertEquals("battle123", battle.battleId());
        // Note: Battle record doesn't have a name field, it's stored in the request
    }

    @Test
    void testGetRobotState() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"id\":\"robot456\", \"name\": \"TestBot\", \"status\": \"IDLE\"}");

        var robot = apiClient.getRobotState("battle123", "robot456");

        assertNotNull(robot);
        assertEquals("robot456", robot.robotId());
        assertEquals("TestBot", robot.name());
    }

    @Test
    void testMoveRobot() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"id\":\"robot456\", \"name\": \"TestBot\", \"status\": \"MOVING\"}");

        var robot = apiClient.moveRobot("battle123", "robot456", "north", 2);

        assertNotNull(robot);
        assertEquals("robot456", robot.robotId());
        assertEquals("TestBot", robot.name());
    }

    @Test
    void testScanRadar() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"detections\": []}");

        var radarResponse = apiClient.scanRadar("battle123", "robot456", 5);

        assertNotNull(radarResponse);
        assertNotNull(radarResponse.getDetections());
    }

    @Test
    void testFireLaser() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"hit\": false}");

        var laserResponse = apiClient.fireLaser("battle123", "robot456", "north", 3);

        assertNotNull(laserResponse);
        assertFalse(laserResponse.isHit());
    }

    @Test
    void testStartBattle() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"id\":\"battle123\", \"status\": \"ACTIVE\"}");

        var battle = apiClient.startBattle("battle123");

        assertNotNull(battle);
        assertEquals("battle123", battle.battleId());
    }

    @Test
    void testErrorResponse() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(400);
        when(mockResponse.body()).thenReturn("{\"message\": \"Bad request\"}");

        var exception = assertThrows(RobotWarsApiException.class, () -> {
            apiClient.getBattle("invalid");
        });

        assertEquals(400, exception.getStatusCode());
        assertEquals("Bad request", exception.getServerMessage());
    }

    @Test
    void testNetworkError() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenThrow(new java.io.IOException("Network error"));

        var exception = assertThrows(RobotWarsApiException.class, () -> {
            apiClient.getBattle("battle123");
        });

        assertTrue(exception.getMessage().contains("Failed to process request"));
        assertTrue(exception.getMessage().contains("Network error"));
    }

    @Test
    void testJsonParsingError() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("invalid json");

        var exception = assertThrows(RobotWarsApiException.class, () -> {
            apiClient.getBattle("battle123");
        });

        assertTrue(exception.getMessage().contains("Failed to parse JSON response"));
    }

    // Note: Network-dependent tests have been removed to ensure test reliability.
    // Integration tests with actual API calls should be in a separate test suite
    // or use mocked HTTP clients for proper unit testing.
}
