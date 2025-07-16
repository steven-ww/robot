package za.co.sww.rwars.robot.client;

import za.co.sww.rwars.robot.model.Battle;
import za.co.sww.rwars.robot.model.Robot;
import za.co.sww.rwars.robot.model.Position;

import java.util.List;

/**
 * Client interface for interacting with the Robot Wars API.
 */
public interface RobotWarsApiClient {
    
    /**
     * Creates a new battle.
     * 
     * @return the created battle
     * @throws RobotWarsApiException if the API call fails
     */
    Battle createBattle() throws RobotWarsApiException;
    
    /**
     * Gets battle information by ID.
     * 
     * @param battleId the battle ID
     * @return the battle information
     * @throws RobotWarsApiException if the API call fails
     */
    Battle getBattle(String battleId) throws RobotWarsApiException;
    
    /**
     * Registers a robot with a battle.
     * 
     * @param battleId the battle ID
     * @param robotName the robot name
     * @return the registered robot
     * @throws RobotWarsApiException if the API call fails
     */
    Robot registerRobot(String battleId, String robotName) throws RobotWarsApiException;
    
    /**
     * Gets the current robot state.
     * 
     * @param robotId the robot ID
     * @return the robot state
     * @throws RobotWarsApiException if the API call fails
     */
    Robot getRobotState(String robotId) throws RobotWarsApiException;
    
    /**
     * Moves the robot in the specified direction.
     * 
     * @param robotId the robot ID
     * @param direction the direction to move (0-359 degrees)
     * @param distance the distance to move
     * @return the updated robot state
     * @throws RobotWarsApiException if the API call fails
     */
    Robot moveRobot(String robotId, int direction, int distance) throws RobotWarsApiException;
    
    /**
     * Scans the arena using radar.
     * 
     * @param robotId the robot ID
     * @param direction the direction to scan (0-359 degrees)
     * @param range the scan range
     * @return list of detected robots
     * @throws RobotWarsApiException if the API call fails
     */
    List<Robot> scanRadar(String robotId, int direction, int range) throws RobotWarsApiException;
    
    /**
     * Fires a laser at the specified target.
     * 
     * @param robotId the robot ID
     * @param direction the direction to fire (0-359 degrees)
     * @param range the firing range
     * @return true if the laser hit a target
     * @throws RobotWarsApiException if the API call fails
     */
    boolean fireLaser(String robotId, int direction, int range) throws RobotWarsApiException;
    
    /**
     * Starts a battle.
     * 
     * @param battleId the battle ID
     * @return the updated battle state
     * @throws RobotWarsApiException if the API call fails
     */
    Battle startBattle(String battleId) throws RobotWarsApiException;
}
