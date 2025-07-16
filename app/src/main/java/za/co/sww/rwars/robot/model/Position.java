package za.co.sww.rwars.robot.model;

/**
 * Represents a position in the arena.
 */
public record Position(
        int x,
        int y
) {
    
    /**
     * Calculates the distance to another position.
     * 
     * @param other the other position
     * @return the distance as a double
     */
    public double distanceTo(Position other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Creates a new position by moving this position by the given offset.
     * 
     * @param dx the x offset
     * @param dy the y offset
     * @return a new Position instance
     */
    public Position moveBy(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }
    
    /**
     * Checks if this position is within the given bounds.
     * 
     * @param maxX the maximum x coordinate
     * @param maxY the maximum y coordinate
     * @return true if the position is within bounds
     */
    public boolean isWithinBounds(int maxX, int maxY) {
        return x >= 0 && x <= maxX && y >= 0 && y <= maxY;
    }
}
