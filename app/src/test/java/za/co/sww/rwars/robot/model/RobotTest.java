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
                10,
                20,
                "EAST",
                75,
                100,
                Robot.RobotStatus.ALIVE,
                "battle-456"
        );

        assertNotNull(robot);
        assertEquals("robot-123", robot.robotId());
        assertEquals("TestBot", robot.name());
        assertEquals(position, robot.position());
        assertEquals("EAST", robot.heading());
        assertEquals(75, robot.hitPoints());
        assertEquals(100, robot.maxHitPoints());
        assertEquals(Robot.RobotStatus.ALIVE, robot.status());
        assertEquals("battle-456", robot.battleId());
    }

    @Test
    void robotStatusCheckers() {
        Robot aliveRobot = new Robot(
                "robot-1",
                "AliveBot",
                0,
                0,
                "NORTH",
                100,
                100,
                Robot.RobotStatus.ALIVE,
                "battle-1"
        );
        
        Robot idleRobot = new Robot(
                "robot-2",
                "IdleBot",
                0,
                0,
                "NORTH",
                100,
                100,
                Robot.RobotStatus.IDLE,
                "battle-1"
        );
        
        Robot destroyedRobot = new Robot(
                "robot-3",
                "DestroyedBot",
                0,
                0,
                "NORTH",
                0,
                100,
                Robot.RobotStatus.DESTROYED,
                "battle-1"
        );
        
        Robot crashedRobot = new Robot(
                "robot-4",
                "CrashedBot",
                0,
                0,
                "NORTH",
                50,
                100,
                Robot.RobotStatus.CRASHED,
                "battle-1"
        );
        
        Robot movingRobot = new Robot(
                "robot-5",
                "MovingBot",
                0,
                0,
                "NORTH",
                80,
                100,
                Robot.RobotStatus.MOVING,
                "battle-1"
        );

        assertTrue(aliveRobot.isAlive());
        assertFalse(aliveRobot.isDestroyed());
        assertFalse(aliveRobot.isCrashed());

        assertTrue(idleRobot.isAlive());
        assertFalse(idleRobot.isDestroyed());
        assertFalse(idleRobot.isCrashed());

        assertFalse(destroyedRobot.isAlive());
        assertTrue(destroyedRobot.isDestroyed());
        assertFalse(destroyedRobot.isCrashed());

        assertFalse(crashedRobot.isAlive());
        assertFalse(crashedRobot.isDestroyed());
        assertTrue(crashedRobot.isCrashed());
        
        assertTrue(movingRobot.isAlive());
        assertFalse(movingRobot.isDestroyed());
        assertFalse(movingRobot.isCrashed());
    }

    @Test
    void robotHealthPercentage() {
        Robot fullHealthRobot = new Robot(
                "robot-1",
                "FullHealthBot",
                0,
                0,
                "NORTH",
                100,
                100,
                Robot.RobotStatus.ALIVE,
                "battle-1"
        );
        
        Robot halfHealthRobot = new Robot(
                "robot-2",
                "HalfHealthBot",
                0,
                0,
                "NORTH",
                50,
                100,
                Robot.RobotStatus.ALIVE,
                "battle-1"
        );
        
        Robot noHealthRobot = new Robot(
                "robot-3",
                "NoHealthBot",
                0,
                0,
                "NORTH",
                0,
                100,
                Robot.RobotStatus.ALIVE,
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
        assertEquals("NORTH", robot.heading());
        assertEquals(100, robot.hitPoints());
        assertEquals(100, robot.maxHitPoints());
        assertEquals(Robot.RobotStatus.IDLE, robot.status());
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
        assertEquals("NORTH", robot.heading());
        assertEquals(100, robot.hitPoints());
        assertEquals(100, robot.maxHitPoints());
        assertEquals(Robot.RobotStatus.IDLE, robot.status());
    }
}
