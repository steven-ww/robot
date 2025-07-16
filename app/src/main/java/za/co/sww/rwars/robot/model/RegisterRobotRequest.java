package za.co.sww.rwars.robot.model;

/**
 * Request for registering a robot in a battle.
 */
public record RegisterRobotRequest(
        String name
) {
    
    /**
     * Creates a new robot registration request.
     * 
     * @param name the robot name
     * @return a new RegisterRobotRequest instance
     */
    public static RegisterRobotRequest create(String name) {
        return new RegisterRobotRequest(name);
    }
}
