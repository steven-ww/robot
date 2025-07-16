package za.co.sww.rwars.robot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Robot in the Robot Wars game.
 */
public record Robot(
        @JsonProperty("id") String robotId,
        String name,
        @JsonProperty("positionX") int positionX,
        @JsonProperty("positionY") int positionY,
        @JsonProperty("direction") String heading,
        int hitPoints,
        int maxHitPoints,
        @JsonProperty("status") RobotStatus status,
        String battleId
) {
    
    /**
     * Robot status enumeration.
     */
    public enum RobotStatus {
        IDLE,
        ALIVE,
        MOVING,
        DESTROYED,
        CRASHED
    }
    
    /**
     * Gets the robot's position as a Position object.
     * 
     * @return the robot's position
     */
    public Position position() {
        return new Position(positionX, positionY);
    }
    
    /**
     * Creates a new robot with the given name.
     * 
     * @param name the robot name
     * @return a new Robot instance
     */
    public static Robot create(String name) {
        return new Robot(
                null,
                name,
                0,
                0,
                "NORTH",
                100,
                100,
                RobotStatus.IDLE,
                null
        );
    }
    
    /**
     * Creates a robot with the given ID and battle ID.
     * 
     * @param robotId the robot ID
     * @param name the robot name
     * @param battleId the battle ID
     * @return a new Robot instance
     */
    public static Robot create(String robotId, String name, String battleId) {
        return new Robot(
                robotId,
                name,
                0,
                0,
                "NORTH",
                100,
                100,
                RobotStatus.IDLE,
                battleId
        );
    }
    
    /**
     * Checks if the robot is alive.
     * 
     * @return true if the robot is alive
     */
    public boolean isAlive() {
        return status == RobotStatus.ALIVE || status == RobotStatus.IDLE || status == RobotStatus.MOVING;
    }
    
    /**
     * Checks if the robot is destroyed.
     * 
     * @return true if the robot is destroyed
     */
    public boolean isDestroyed() {
        return status == RobotStatus.DESTROYED;
    }
    
    /**
     * Checks if the robot has crashed.
     * 
     * @return true if the robot has crashed
     */
    public boolean isCrashed() {
        return status == RobotStatus.CRASHED;
    }
    
    /**
     * Gets the health percentage of the robot.
     * 
     * @return the health percentage (0-100)
     */
    public double getHealthPercentage() {
        return (double) hitPoints / maxHitPoints * 100.0;
    }
}
