package za.co.sww.rwars.robot.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void positionCanBeCreated() {
        Position position = new Position(10, 20);
        
        assertNotNull(position);
        assertEquals(10, position.x());
        assertEquals(20, position.y());
    }

    @Test
    void positionDistanceCalculation() {
        Position position1 = new Position(0, 0);
        Position position2 = new Position(3, 4);
        Position position3 = new Position(10, 10);
        
        assertEquals(0.0, position1.distanceTo(position1), 0.01);
        assertEquals(5.0, position1.distanceTo(position2), 0.01);
        assertEquals(5.0, position2.distanceTo(position1), 0.01);
        assertEquals(14.14, position1.distanceTo(position3), 0.01);
    }

    @Test
    void positionMovement() {
        Position original = new Position(10, 10);
        
        Position moved = original.moveBy(5, 3);
        
        assertEquals(15, moved.x());
        assertEquals(13, moved.y());
        assertEquals(10, original.x()); // Original should be unchanged
        assertEquals(10, original.y());
    }

    @Test
    void positionBoundaryChecks() {
        Position insidePosition = new Position(50, 50);
        Position outsidePosition = new Position(-10, 150);
        Position borderPosition = new Position(0, 100);
        
        assertTrue(insidePosition.isWithinBounds(100, 100));
        assertFalse(outsidePosition.isWithinBounds(100, 100));
        assertTrue(borderPosition.isWithinBounds(100, 100));
    }

    @Test
    void positionBoundaryChecksWithNegativeCoordinates() {
        Position negativePosition = new Position(-5, -10);
        Position edgePosition = new Position(0, 0);
        
        assertFalse(negativePosition.isWithinBounds(100, 100));
        assertTrue(edgePosition.isWithinBounds(100, 100));
    }

    @Test
    void positionBoundaryChecksWithExactBounds() {
        Position exactBoundsPosition = new Position(100, 100);
        
        assertTrue(exactBoundsPosition.isWithinBounds(100, 100));
        assertFalse(exactBoundsPosition.isWithinBounds(99, 99));
    }

    @Test
    void positionEquality() {
        Position position1 = new Position(10, 20);
        Position position2 = new Position(10, 20);
        Position position3 = new Position(20, 10);
        
        assertEquals(position1, position2);
        assertNotEquals(position1, position3);
        assertEquals(position1.hashCode(), position2.hashCode());
    }
}
