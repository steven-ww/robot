package za.co.sww.rwars.robot.model;

/**
 * Request for creating a new battle with optional dimensions and timing.
 */
public class CreateBattleRequest {
    private String name; // Required
    private Integer width; // Optional (10-100)
    private Integer height; // Optional (10-100)
    private Double robotMovementTimeSeconds; // Optional (0.1-10.0)

    public CreateBattleRequest() {
    }

    public CreateBattleRequest(String name) {
        this.name = name;
    }

    public CreateBattleRequest(String name, Integer width, Integer height, Double robotMovementTimeSeconds) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.robotMovementTimeSeconds = robotMovementTimeSeconds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getRobotMovementTimeSeconds() {
        return robotMovementTimeSeconds;
    }

    public void setRobotMovementTimeSeconds(Double robotMovementTimeSeconds) {
        this.robotMovementTimeSeconds = robotMovementTimeSeconds;
    }
}
