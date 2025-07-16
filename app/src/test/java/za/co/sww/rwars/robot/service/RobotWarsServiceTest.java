package za.co.sww.rwars.robot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.sww.rwars.robot.client.RobotWarsApiClient;
import za.co.sww.rwars.robot.client.RobotWarsApiException;
import za.co.sww.rwars.robot.model.Battle;
import za.co.sww.rwars.robot.model.Robot;
import za.co.sww.rwars.robot.model.Position;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RobotWarsServiceTest {

    @Mock
    private RobotWarsApiClient mockApiClient;

    private RobotWarsService robotWarsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        robotWarsService = new RobotWarsService(mockApiClient);
    }

    @Test
    void serviceCanBeCreated() {
        assertNotNull(robotWarsService);
    }

    @Test
    void createBattleCallsApiClient() throws RobotWarsApiException {
        // Arrange
        Battle expectedBattle = new Battle(
                "battle-123",
                "WAITING",
                Instant.now(),
                null,
                null,
                List.of(),
                null
        );
        when(mockApiClient.createBattle()).thenReturn(expectedBattle);

        // Act
        Battle result = robotWarsService.createBattle();

        // Assert
        assertNotNull(result);
        assertEquals("battle-123", result.battleId());
        assertEquals("WAITING", result.status());
        verify(mockApiClient).createBattle();
    }

    @Test
    void getBattleCallsApiClient() throws RobotWarsApiException {
        // Arrange
        String battleId = "battle-123";
        Battle expectedBattle = new Battle(
                battleId,
                "ACTIVE",
                Instant.now(),
                Instant.now(),
                null,
                List.of("robot-1", "robot-2"),
                null
        );
        when(mockApiClient.getBattle(battleId)).thenReturn(expectedBattle);

        // Act
        Battle result = robotWarsService.getBattle(battleId);

        // Assert
        assertNotNull(result);
        assertEquals(battleId, result.battleId());
        assertEquals("ACTIVE", result.status());
        verify(mockApiClient).getBattle(battleId);
    }

    @Test
    void registerRobotCallsApiClient() throws RobotWarsApiException {
        // Arrange
        String battleId = "battle-123";
        String robotName = "TestBot";
        Robot expectedRobot = new Robot(
                "robot-456",
                robotName,
                new Position(0, 0),
                0,
                100,
                100,
                Robot.RobotStatus.ALIVE,
                battleId
        );
        when(mockApiClient.registerRobot(battleId, robotName)).thenReturn(expectedRobot);

        // Act
        Robot result = robotWarsService.registerRobot(battleId, robotName);

        // Assert
        assertNotNull(result);
        assertEquals("robot-456", result.robotId());
        assertEquals(robotName, result.name());
        assertEquals(battleId, result.battleId());
        verify(mockApiClient).registerRobot(battleId, robotName);
    }

    @Test
    void getRobotStateCallsApiClient() throws RobotWarsApiException {
        // Arrange
        String robotId = "robot-456";
        Robot expectedRobot = new Robot(
                robotId,
                "TestBot",
                new Position(10, 20),
                90,
                75,
                100,
                Robot.RobotStatus.ALIVE,
                "battle-123"
        );
        when(mockApiClient.getRobotState(robotId)).thenReturn(expectedRobot);

        // Act
        Robot result = robotWarsService.getRobotState(robotId);

        // Assert
        assertNotNull(result);
        assertEquals(robotId, result.robotId());
        assertEquals("TestBot", result.name());
        assertEquals(75, result.hitPoints());
        verify(mockApiClient).getRobotState(robotId);
    }

    @Test
    void startBattleCallsApiClient() throws RobotWarsApiException {
        // Arrange
        String battleId = "battle-123";
        Battle expectedBattle = new Battle(
                battleId,
                "ACTIVE",
                Instant.now(),
                Instant.now(),
                null,
                List.of("robot-1", "robot-2"),
                null
        );
        when(mockApiClient.startBattle(battleId)).thenReturn(expectedBattle);

        // Act
        Battle result = robotWarsService.startBattle(battleId);

        // Assert
        assertNotNull(result);
        assertEquals(battleId, result.battleId());
        assertEquals("ACTIVE", result.status());
        verify(mockApiClient).startBattle(battleId);
    }

    @Test
    void createBattleHandlesApiException() throws RobotWarsApiException {
        // Arrange
        when(mockApiClient.createBattle()).thenThrow(new RobotWarsApiException("API Error"));

        // Act & Assert
        assertThrows(RobotWarsApiException.class, () -> {
            robotWarsService.createBattle();
        });
        verify(mockApiClient).createBattle();
    }

    @Test
    void registerRobotHandlesApiException() throws RobotWarsApiException {
        // Arrange
        String battleId = "battle-123";
        String robotName = "TestBot";
        when(mockApiClient.registerRobot(battleId, robotName))
                .thenThrow(new RobotWarsApiException("Registration failed", 400));

        // Act & Assert
        RobotWarsApiException exception = assertThrows(RobotWarsApiException.class, () -> {
            robotWarsService.registerRobot(battleId, robotName);
        });
        assertEquals("Registration failed", exception.getMessage());
        assertEquals(400, exception.getStatusCode());
        verify(mockApiClient).registerRobot(battleId, robotName);
    }
}
