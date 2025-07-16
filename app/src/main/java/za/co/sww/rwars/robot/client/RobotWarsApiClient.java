package za.co.sww.rwars.robot.client;

import za.co.sww.rwars.robot.model.Battle;
import za.co.sww.rwars.robot.model.Robot;
import za.co.sww.rwars.robot.model.RadarResponse;
import za.co.sww.rwars.robot.model.LaserResponse;
import za.co.sww.rwars.robot.model.CreateBattleRequest;

/**
 * Client interface for interacting with the Robot Wars API.
 */
public interface RobotWarsApiClient {
    
    /**
     * Creates a new battle.
     * 
     * @param request the battle creation request
     * @return the created battle
     * @throws RobotWarsApiException if the API call fails
     */
    Battle createBattle(CreateBattleRequest request) throws RobotWarsApiException;
    
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
     * @param battleId the battle ID
     * @param robotId the robot ID
     * @return the robot state
     * @throws RobotWarsApiException if the API call fails
     */
    Robot getRobotState(String battleId, String robotId) throws RobotWarsApiException;
    
    /**
     * Moves the robot in the specified direction.
     * 
     * @param battleId the battle ID
     * @param robotId the robot ID
     * @param direction the direction to move (string like "NORTH", "SOUTH", etc.)
     * @param blocks the number of blocks to move
     * @return the updated robot state
     * @throws RobotWarsApiException if the API call fails
     */
    Robot moveRobot(String battleId, String robotId, String direction, int blocks) throws RobotWarsApiException;
    
    /**
     * Scans the arena using radar.
     * 
     * @param battleId the battle ID
     * @param robotId the robot ID
     * @param range the scan range
     * @return radar scan response with detections
     * @throws RobotWarsApiException if the API call fails
     */
    RadarResponse scanRadar(String battleId, String robotId, int range) throws RobotWarsApiException;
    
    /**
     * Fires a laser at the specified target.
     * 
     * @param battleId the battle ID
     * @param robotId the robot ID
     * @param direction the direction to fire (string like "NORTH", "SOUTH", etc.)
     * @param range the firing range
     * @return laser response with hit information
     * @throws RobotWarsApiException if the API call fails
     */
    LaserResponse fireLaser(String battleId, String robotId, String direction, int range) throws RobotWarsApiException;
    
    /**
     * Starts a battle.
     * 
     * @param battleId the battle ID
     * @return the updated battle state
     * @throws RobotWarsApiException if the API call fails
     */
    Battle startBattle(String battleId) throws RobotWarsApiException;
}
