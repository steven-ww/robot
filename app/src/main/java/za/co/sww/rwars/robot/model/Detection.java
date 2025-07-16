package za.co.sww.rwars.robot.model;

/**
 * Detection details in a radar scan.
 */
public class Detection {
    private int x;
    private int y;
    private DetectionType type;
    private String details;

    public Detection() {
    }

    public Detection(int x, int y, DetectionType type, String details) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.details = details;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public DetectionType getType() {
        return type;
    }

    public void setType(DetectionType type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
