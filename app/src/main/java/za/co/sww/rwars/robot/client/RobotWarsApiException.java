package za.co.sww.rwars.robot.client;

/**
 * Exception thrown when Robot Wars API calls fail.
 */
public class RobotWarsApiException extends Exception {
    
    private final int statusCode;
    
    /**
     * Creates a new RobotWarsApiException with the given message.
     * 
     * @param message the error message
     */
    public RobotWarsApiException(String message) {
        super(message);
        this.statusCode = 0;
    }
    
    /**
     * Creates a new RobotWarsApiException with the given message and cause.
     * 
     * @param message the error message
     * @param cause the cause of the exception
     */
    public RobotWarsApiException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 0;
    }
    
    /**
     * Creates a new RobotWarsApiException with the given message and status code.
     * 
     * @param message the error message
     * @param statusCode the HTTP status code
     */
    public RobotWarsApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    
    /**
     * Gets the HTTP status code associated with this exception.
     * 
     * @return the status code, or 0 if not available
     */
    public int getStatusCode() {
        return statusCode;
    }
}
