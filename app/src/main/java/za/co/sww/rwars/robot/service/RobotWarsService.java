package za.co.sww.rwars.robot.service;

import za.co.sww.rwars.robot.client.RobotWarsApiClient;
import za.co.sww.rwars.robot.client.RobotWarsApiException;
import za.co.sww.rwars.robot.model.Battle;
import za.co.sww.rwars.robot.model.Robot;
import za.co.sww.rwars.robot.model.CreateBattleRequest;
import za.co.sww.rwars.robot.model.RadarResponse;
import za.co.sww.rwars.robot.model.LaserResponse;

/**
 * Service class to handle robot wars game operations.
 */
public class RobotWarsService {
    
    private final RobotWarsApiClient apiClient;
    
    /**
     * Creates a new RobotWarsService with the given API client.
     * 
     * @param apiClient the API client to use
     */
    public RobotWarsService(RobotWarsApiClient apiClient) {
        this.apiClient = apiClient;
    }
    
    /**
     * Creates a new battle.
     * 
     * @param request the battle creation request
     * @return the created battle
     * @throws RobotWarsApiException if the API call fails
     */
    public Battle createBattle(CreateBattleRequest request) throws RobotWarsApiException {
        return apiClient.createBattle(request);
    }
    
    /**
     * Registers a robot with the given name to a battle.
     * 
     * @param battleId the battle ID to register the robot with
     * @param robotName the name of the robot to register
     * @return the registered robot
     * @throws RobotWarsApiException if the API call fails
     */
    public Robot registerRobot(String battleId, String robotName) throws RobotWarsApiException {
        return apiClient.registerRobot(battleId, robotName);
    }
    
    /**
     * Gets the current state of a battle.
     * 
     * @param battleId the battle ID
     * @return the battle state
     * @throws RobotWarsApiException if the API call fails
     */
    public Battle getBattle(String battleId) throws RobotWarsApiException {
        return apiClient.getBattle(battleId);
    }
    
    /**
     * Starts a battle.
     * 
     * @param battleId the battle ID to start
     * @return the updated battle state
     * @throws RobotWarsApiException if the API call fails
     */
    public Battle startBattle(String battleId) throws RobotWarsApiException {
        return apiClient.startBattle(battleId);
    }
    
    /**
     * Gets the current state of a robot.
     * 
     * @param battleId the battle ID
     * @param robotId the robot ID
     * @return the robot state
     * @throws RobotWarsApiException if the API call fails
     */
    public Robot getRobotState(String battleId, String robotId) throws RobotWarsApiException {
        return apiClient.getRobotState(battleId, robotId);
    }
    
    /**
     * Moves a robot in the specified direction.
     * 
     * @param battleId the battle ID
     * @param robotId the robot ID
     * @param direction the direction to move
     * @param blocks the number of blocks to move
     * @return the updated robot state
     * @throws RobotWarsApiException if the API call fails
     */
    public Robot moveRobot(String battleId, String robotId, String direction, int blocks) throws RobotWarsApiException {
        return apiClient.moveRobot(battleId, robotId, direction, blocks);
    }
    
    /**
     * Performs a radar scan.
     * 
     * @param battleId the battle ID
     * @param robotId the robot ID
     * @param range the scan range
     * @return the radar response
     * @throws RobotWarsApiException if the API call fails
     */
    public RadarResponse scanRadar(String battleId, String robotId, int range) throws RobotWarsApiException {
        return apiClient.scanRadar(battleId, robotId, range);
    }
    
    /**
     * Fires a laser.
     * 
     * @param battleId the battle ID
     * @param robotId the robot ID
     * @param direction the direction to fire
     * @param range the firing range
     * @return the laser response
     * @throws RobotWarsApiException if the API call fails
     */
    public LaserResponse fireLaser(String battleId, String robotId, String direction, int range) throws RobotWarsApiException {
        return apiClient.fireLaser(battleId, robotId, direction, range);
    }
}
