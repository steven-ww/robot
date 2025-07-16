package za.co.sww.rwars.robot.service;

import za.co.sww.rwars.robot.client.RobotWarsApiClient;
import za.co.sww.rwars.robot.client.RobotWarsApiException;
import za.co.sww.rwars.robot.model.Battle;
import za.co.sww.rwars.robot.model.Robot;

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
     * @return the created battle
     * @throws RobotWarsApiException if the API call fails
     */
    public Battle createBattle() throws RobotWarsApiException {
        return apiClient.createBattle();
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
     * @param robotId the robot ID
     * @return the robot state
     * @throws RobotWarsApiException if the API call fails
     */
    public Robot getRobotState(String robotId) throws RobotWarsApiException {
        return apiClient.getRobotState(robotId);
    }
}
