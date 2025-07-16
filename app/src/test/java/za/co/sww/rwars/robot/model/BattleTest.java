package za.co.sww.rwars.robot.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    @Test
    void battleCanBeCreated() {
        LocalDateTime now = LocalDateTime.now();
        Robot robot1 = new Robot("robot-1", "Robot1", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, "battle-123");
        Robot robot2 = new Robot("robot-2", "Robot2", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, "battle-123");
        Battle battle = new Battle(
                "battle-123",
                "WAITING",
                now,
                null,
                null,
                List.of(robot1, robot2),
                null
        );

        assertNotNull(battle);
        assertEquals("battle-123", battle.battleId());
        assertEquals("WAITING", battle.status());
        assertEquals(now, battle.createdAt());
        assertEquals(2, battle.getRobotIds().size());
        assertNull(battle.winner());
    }

    @Test
    void battleStatusCheckers() {
        LocalDateTime now = LocalDateTime.now();
        
        Robot robot1 = new Robot("robot-1", "Robot1", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, "battle-1");
        Robot robot2 = new Robot("robot-2", "Robot2", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, "battle-2");
        Robot robot3 = new Robot("robot-1", "Robot1", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, "battle-3");
        Robot robot4 = new Robot("robot-2", "Robot2", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, "battle-3");
        
        Battle waitingBattle = new Battle(
                "battle-1",
                "WAITING",
                now,
                null,
                null,
                List.of(robot1),
                null
        );
        
        Battle activeBattle = new Battle(
                "battle-2",
                "ACTIVE",
                now,
                LocalDateTime.now(),
                null,
                List.of(robot1, robot2),
                null
        );
        
        Battle finishedBattle = new Battle(
                "battle-3",
                "FINISHED",
                now,
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of(robot3, robot4),
                "robot-1"
        );

        assertTrue(waitingBattle.isWaiting());
        assertFalse(waitingBattle.isActive());
        assertFalse(waitingBattle.isFinished());

        assertFalse(activeBattle.isWaiting());
        assertTrue(activeBattle.isActive());
        assertFalse(activeBattle.isFinished());

        assertFalse(finishedBattle.isWaiting());
        assertFalse(finishedBattle.isActive());
        assertTrue(finishedBattle.isFinished());
    }

    @Test
    void battleCreateFactory() {
        Battle battle = Battle.create("battle-123");
        
        assertNotNull(battle);
        assertEquals("battle-123", battle.battleId());
        assertEquals("WAITING", battle.status());
        assertNotNull(battle.createdAt());
        assertNull(battle.startedAt());
        assertNull(battle.finishedAt());
        assertEquals(0, battle.getRobotIds().size());
        assertNull(battle.winner());
    }

    @Test
    void battleWithWinner() {
        LocalDateTime now = LocalDateTime.now();
        Robot robot1 = new Robot("robot-1", "Robot1", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, "battle-123");
        Robot robot2 = new Robot("robot-2", "Robot2", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, "battle-123");
        Battle battle = new Battle(
                "battle-123",
                "FINISHED",
                now,
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of(robot1, robot2),
                "robot-1"
        );

        assertEquals("robot-1", battle.winner());
        assertNotNull(battle.winner());
    }

    @Test
    void battleWithoutWinner() {
        LocalDateTime now = LocalDateTime.now();
        Robot robot1 = new Robot("robot-1", "Robot1", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, "battle-123");
        Robot robot2 = new Robot("robot-2", "Robot2", 0, 0, "NORTH", 100, 100, Robot.RobotStatus.ALIVE, "battle-123");
        Battle battle = new Battle(
                "battle-123",
                "WAITING",
                now,
                null,
                null,
                List.of(robot1, robot2),
                null
        );

        assertNull(battle.winner());
    }
}
