package za.co.sww.rwars.robot.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    @Test
    void robotCanBeCreated() {
        Position position = new Position(10, 20);
        Robot robot = new Robot(
                "robot-123",
                "TestBot",
                position,
                90,
                75,
                100,
                Robot.RobotStatus.ALIVE,
                "battle-456"
        );

        assertNotNull(robot);
        assertEquals("robot-123", robot.robotId());
        assertEquals("TestBot", robot.name());
        assertEquals(position, robot.position());
        assertEquals(90, robot.heading());
        assertEquals(75, robot.hitPoints());
        assertEquals(100, robot.maxHitPoints());
        assertEquals(Robot.RobotStatus.ALIVE, robot.status());
        assertEquals("battle-456", robot.battleId());
    }

    @Test
    void robotStatusCheckers() {
        Position position = new Position(0, 0);
        
        Robot aliveRobot = new Robot(
                "robot-1",
                "AliveBot",
                position,
                0,
                50,
                100,
                Robot.RobotStatus.ALIVE,
                "battle-1"
        );
        
        Robot destroyedRobot = new Robot(
                "robot-2",
                "DestroyedBot",
                position,
                0,
                0,
                100,
                Robot.RobotStatus.DESTROYED,
                "battle-1"
        );
        
        Robot crashedRobot = new Robot(
                "robot-3",
                "CrashedBot",
                position,
                0,
                25,
                100,
                Robot.RobotStatus.CRASHED,
                "battle-1"
        );

        assertTrue(aliveRobot.isAlive());
        assertFalse(aliveRobot.isDestroyed());
        assertFalse(aliveRobot.isCrashed());

        assertFalse(destroyedRobot.isAlive());
        assertTrue(destroyedRobot.isDestroyed());
        assertFalse(destroyedRobot.isCrashed());

        assertFalse(crashedRobot.isAlive());
        assertFalse(crashedRobot.isDestroyed());
        assertTrue(crashedRobot.isCrashed());
    }

    @Test
    void robotHealthPercentage() {
        Position position = new Position(0, 0);
        
        Robot fullHealthRobot = new Robot(
                "robot-1",
                "FullHealth",
                position,
                0,
                100,
                100,
                Robot.RobotStatus.ALIVE,
                "battle-1"
        );
        
        Robot halfHealthRobot = new Robot(
                "robot-2",
                "HalfHealth",
                position,
                0,
                50,
                100,
                Robot.RobotStatus.ALIVE,
                "battle-1"
        );
        
        Robot noHealthRobot = new Robot(
                "robot-3",
                "NoHealth",
                position,
                0,
                0,
                100,
                Robot.RobotStatus.DESTROYED,
                "battle-1"
        );

        assertEquals(100.0, fullHealthRobot.getHealthPercentage(), 0.01);
        assertEquals(50.0, halfHealthRobot.getHealthPercentage(), 0.01);
        assertEquals(0.0, noHealthRobot.getHealthPercentage(), 0.01);
    }

    @Test
    void robotCreateWithName() {
        Robot robot = Robot.create("TestBot");
        
        assertNotNull(robot);
        assertNull(robot.robotId());
        assertEquals("TestBot", robot.name());
        assertEquals(new Position(0, 0), robot.position());
        assertEquals(0, robot.heading());
        assertEquals(100, robot.hitPoints());
        assertEquals(100, robot.maxHitPoints());
        assertEquals(Robot.RobotStatus.ALIVE, robot.status());
        assertNull(robot.battleId());
    }

    @Test
    void robotCreateWithIdAndBattle() {
        Robot robot = Robot.create("robot-123", "TestBot", "battle-456");
        
        assertNotNull(robot);
        assertEquals("robot-123", robot.robotId());
        assertEquals("TestBot", robot.name());
        assertEquals("battle-456", robot.battleId());
        assertEquals(new Position(0, 0), robot.position());
        assertEquals(0, robot.heading());
        assertEquals(100, robot.hitPoints());
        assertEquals(100, robot.maxHitPoints());
        assertEquals(Robot.RobotStatus.ALIVE, robot.status());
    }
}
