package za.co.sww.rwars.robot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.sww.rwars.robot.client.RobotWarsApiClient;
import za.co.sww.rwars.robot.client.RobotWarsApiException;
import za.co.sww.rwars.robot.model.Battle;
import za.co.sww.rwars.robot.model.Robot;
import za.co.sww.rwars.robot.model.CreateBattleRequest;
import za.co.sww.rwars.robot.model.RadarResponse;
import za.co.sww.rwars.robot.model.LaserResponse;

import java.time.LocalDateTime;
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
        CreateBattleRequest request = new CreateBattleRequest("TestBattle");
        Battle expectedBattle = new Battle(
                "battle-123",
                "WAITING",
                LocalDateTime.now(),
                null,
                null,
                List.of(),
                null
        );
        when(mockApiClient.createBattle(request)).thenReturn(expectedBattle);

        // Act
        Battle result = robotWarsService.createBattle(request);

        // Assert
        assertNotNull(result);
        assertEquals("battle-123", result.battleId());
        assertEquals("WAITING", result.status());
        verify(mockApiClient).createBattle(request);
    }

    @Test
    void getBattleCallsApiClient() throws RobotWarsApiException {
        // Arrange
        String battleId = "battle-123";
        Robot robot1 = new Robot("robot-1", "Robot1", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, battleId);
        Robot robot2 = new Robot("robot-2", "Robot2", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, battleId);
        Battle expectedBattle = new Battle(
                battleId,
                "ACTIVE",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                List.of(robot1, robot2),
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
                0,
                0,
                "NORTH",
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
        String battleId = "battle-123";
        String robotId = "robot-456";
        Robot expectedRobot = new Robot(
                robotId,
                "TestBot",
                10,
                20,
                "EAST",
                75,
                100,
                Robot.RobotStatus.ALIVE,
                battleId
        );
        when(mockApiClient.getRobotState(battleId, robotId)).thenReturn(expectedRobot);

        // Act
        Robot result = robotWarsService.getRobotState(battleId, robotId);

        // Assert
        assertNotNull(result);
        assertEquals(robotId, result.robotId());
        assertEquals("TestBot", result.name());
        assertEquals(75, result.hitPoints());
        verify(mockApiClient).getRobotState(battleId, robotId);
    }

    @Test
    void startBattleCallsApiClient() throws RobotWarsApiException {
        // Arrange
        String battleId = "battle-123";
        Robot robot1 = new Robot("robot-1", "Robot1", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, battleId);
        Robot robot2 = new Robot("robot-2", "Robot2", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, battleId);
        Battle expectedBattle = new Battle(
                battleId,
                "ACTIVE",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                List.of(robot1, robot2),
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
        CreateBattleRequest request = new CreateBattleRequest("TestBattle");
        when(mockApiClient.createBattle(request)).thenThrow(new RobotWarsApiException("API Error"));

        // Act & Assert
        assertThrows(RobotWarsApiException.class, () -> {
            robotWarsService.createBattle(request);
        });
        verify(mockApiClient).createBattle(request);
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

    @Test
    void moveRobotCallsApiClient() throws RobotWarsApiException {
        // Arrange
        String battleId = "battle-123";
        String robotId = "robot-456";
        String direction = "NORTH";
        int blocks = 5;
        Robot expectedRobot = new Robot(
                robotId,
                "TestBot",
                0,
                5,
                "NORTH",
                100,
                100,
                Robot.RobotStatus.ALIVE,
                battleId
        );
        when(mockApiClient.moveRobot(battleId, robotId, direction, blocks)).thenReturn(expectedRobot);

        // Act
        Robot result = robotWarsService.moveRobot(battleId, robotId, direction, blocks);

        // Assert
        assertNotNull(result);
        assertEquals(robotId, result.robotId());
        assertEquals(0, result.position().x());
        assertEquals(5, result.position().y());
        verify(mockApiClient).moveRobot(battleId, robotId, direction, blocks);
    }

    @Test
    void scanRadarCallsApiClient() throws RobotWarsApiException {
        // Arrange
        String battleId = "battle-123";
        String robotId = "robot-456";
        int range = 10;
        RadarResponse expectedResponse = new RadarResponse();
        expectedResponse.setRange(range);
        when(mockApiClient.scanRadar(battleId, robotId, range)).thenReturn(expectedResponse);

        // Act
        RadarResponse result = robotWarsService.scanRadar(battleId, robotId, range);

        // Assert
        assertNotNull(result);
        assertEquals(range, result.getRange());
        verify(mockApiClient).scanRadar(battleId, robotId, range);
    }

    @Test
    void fireLaserCallsApiClient() throws RobotWarsApiException {
        // Arrange
        String battleId = "battle-123";
        String robotId = "robot-456";
        String direction = "EAST";
        int range = 20;
        LaserResponse expectedResponse = new LaserResponse();
        expectedResponse.setHit(true);
        expectedResponse.setDirection(direction);
        expectedResponse.setRange(range);
        when(mockApiClient.fireLaser(battleId, robotId, direction, range)).thenReturn(expectedResponse);

        // Act
        LaserResponse result = robotWarsService.fireLaser(battleId, robotId, direction, range);

        // Assert
        assertNotNull(result);
        assertTrue(result.isHit());
        assertEquals(direction, result.getDirection());
        assertEquals(range, result.getRange());
        verify(mockApiClient).fireLaser(battleId, robotId, direction, range);
    }
}
