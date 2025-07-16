package za.co.sww.rwars.robot.model;

import java.util.List;

/**
 * Response from a laser firing operation, detailing results.
 */
public class LaserResponse {
    private boolean hit;
    private String hitRobotId;
    private String hitRobotName;
    private int damageDealt;
    private int range;
    private String direction;
    private List<Position> laserPath;
    private Position hitPosition;
    private String blockedBy;

    public LaserResponse() {
    }

    public LaserResponse(boolean hit, String hitRobotId, String hitRobotName, int damageDealt, 
                        int range, String direction, List<Position> laserPath, Position hitPosition, 
                        String blockedBy) {
        this.hit = hit;
        this.hitRobotId = hitRobotId;
        this.hitRobotName = hitRobotName;
        this.damageDealt = damageDealt;
        this.range = range;
        this.direction = direction;
        this.laserPath = laserPath;
        this.hitPosition = hitPosition;
        this.blockedBy = blockedBy;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public String getHitRobotId() {
        return hitRobotId;
    }

    public void setHitRobotId(String hitRobotId) {
        this.hitRobotId = hitRobotId;
    }

    public String getHitRobotName() {
        return hitRobotName;
    }

    public void setHitRobotName(String hitRobotName) {
        this.hitRobotName = hitRobotName;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<Position> getLaserPath() {
        return laserPath;
    }

    public void setLaserPath(List<Position> laserPath) {
        this.laserPath = laserPath;
    }

    public Position getHitPosition() {
        return hitPosition;
    }

    public void setHitPosition(Position hitPosition) {
        this.hitPosition = hitPosition;
    }

    public String getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(String blockedBy) {
        this.blockedBy = blockedBy;
    }
}
