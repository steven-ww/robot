package za.co.sww.rwars.robot.model;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    @Test
    void battleCanBeCreated() {
        Instant now = Instant.now();
        Battle battle = new Battle(
                "battle-123",
                "WAITING",
                now,
                null,
                null,
                List.of("robot-1", "robot-2"),
                null
        );

        assertNotNull(battle);
        assertEquals("battle-123", battle.battleId());
        assertEquals("WAITING", battle.status());
        assertEquals(now, battle.createdAt());
        assertEquals(2, battle.robotIds().size());
        assertNull(battle.winner());
    }

    @Test
    void battleStatusCheckers() {
        Instant now = Instant.now();
        
        Battle waitingBattle = new Battle(
                "battle-1",
                "WAITING",
                now,
                null,
                null,
                List.of("robot-1"),
                null
        );
        
        Battle activeBattle = new Battle(
                "battle-2",
                "ACTIVE",
                now,
                Instant.now(),
                null,
                List.of("robot-1", "robot-2"),
                null
        );
        
        Battle finishedBattle = new Battle(
                "battle-3",
                "FINISHED",
                now,
                Instant.now(),
                Instant.now(),
                List.of("robot-1", "robot-2"),
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
        assertEquals(0, battle.robotIds().size());
        assertNull(battle.winner());
    }

    @Test
    void battleWithWinner() {
        Instant now = Instant.now();
        Battle battle = new Battle(
                "battle-123",
                "FINISHED",
                now,
                Instant.now(),
                Instant.now(),
                List.of("robot-1", "robot-2"),
                "robot-1"
        );

        assertEquals("robot-1", battle.winner());
        assertNotNull(battle.winner());
    }

    @Test
    void battleWithoutWinner() {
        Instant now = Instant.now();
        Battle battle = new Battle(
                "battle-123",
                "WAITING",
                now,
                null,
                null,
                List.of("robot-1", "robot-2"),
                null
        );

        assertNull(battle.winner());
    }
}
