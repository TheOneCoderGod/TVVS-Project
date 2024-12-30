package badIceCream.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    private Position position;

    @BeforeEach
    void setUp() {
        position = new Position(5, 5);
    }

    @Test
    @DisplayName("Constructor sets X and Y correctly")
    void testConstructor() {
        assertEquals(5, position.getX());
        assertEquals(5, position.getY());
    }

    @Test
    @DisplayName("getLeft() returns Position(x-1, y)")
    void testGetLeft() {
        Position left = position.getLeft();
        assertEquals(4, left.getX());
        assertEquals(5, left.getY());
    }

    @Test
    @DisplayName("getRight() returns Position(x+1, y)")
    void testGetRight() {
        Position right = position.getRight();
        assertEquals(6, right.getX());
        assertEquals(5, right.getY());
    }

    @Test
    @DisplayName("getUp() returns Position(x, y-1)")
    void testGetUp() {
        Position up = position.getUp();
        assertEquals(5, up.getX());
        assertEquals(4, up.getY());
    }

    @Test
    @DisplayName("getDown() returns Position(x, y+1)")
    void testGetDown() {
        Position down = position.getDown();
        assertEquals(5, down.getX());
        assertEquals(6, down.getY());
    }

    @Test
    @DisplayName("setX / setY update coordinates")
    void testSetters() {
        position.setX(10);
        position.setY(12);
        assertEquals(10, position.getX());
        assertEquals(12, position.getY());
    }

    @Test
    @DisplayName("equals() returns true for same coordinates, false otherwise")
    void testEquals() {
        Position p2 = new Position(5,5);
        Position p3 = new Position(6,5);
        assertTrue(position.equals(p2));
        assertFalse(position.equals(p3));
    }

    @Test
    @DisplayName("hashCode() consistent with equals()")
    void testHashCode() {
        Position p2 = new Position(5,5);
        assertEquals(position.hashCode(), p2.hashCode());
    }
}
