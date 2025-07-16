package za.co.sww.rwars.robot.model;

import java.time.Instant;
import java.util.List;

/**
 * Represents a Robot Wars battle.
 */
public record Battle(
        String battleId,
        String status,
        Instant createdAt,
        Instant startedAt,
        Instant finishedAt,
        List<String> robotIds,
        String winner
) {
    
    /**
     * Creates a new battle with the given ID.
     * 
     * @param battleId the battle ID
     * @return a new Battle instance
     */
    public static Battle create(String battleId) {
        return new Battle(
                battleId,
                "WAITING",
                Instant.now(),
                null,
                null,
                List.of(),
                null
        );
    }
    
    /**
     * Checks if the battle is waiting for robots.
     * 
     * @return true if the battle is in WAITING status
     */
    public boolean isWaiting() {
        return "WAITING".equals(status);
    }
    
    /**
     * Checks if the battle is currently active.
     * 
     * @return true if the battle is in ACTIVE status
     */
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    
    /**
     * Checks if the battle has finished.
     * 
     * @return true if the battle is in FINISHED status
     */
    public boolean isFinished() {
        return "FINISHED".equals(status);
    }
}
