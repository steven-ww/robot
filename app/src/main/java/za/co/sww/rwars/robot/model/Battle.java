package za.co.sww.rwars.robot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a Robot Wars battle.
 */
public record Battle(
        @JsonProperty("id") String battleId,
        @JsonProperty("state") String status,
        LocalDateTime createdAt,
        LocalDateTime startedAt,
        LocalDateTime finishedAt,
        @JsonProperty("robots") List<Robot> robots,
        @JsonProperty("winnerName") String winner
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
                LocalDateTime.now(),
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
    
    /**
     * Gets the robot IDs for backward compatibility.
     * 
     * @return a list of robot IDs
     */
    public List<String> getRobotIds() {
        return robots.stream()
                .map(Robot::robotId)
                .toList();
    }
}
