package badIceCream.model.game.elements;

import badIceCream.GUI.GUI;
import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IceCreamTest {

    private IceCream iceCream;

    @BeforeEach
    void setUp() {
        iceCream = new IceCream(2, 5);
    }

    @Test
    @DisplayName("Constructor sets position, alive=true, strawberry=false")
    void testConstructor() {
        assertEquals(2, iceCream.getPosition().getX());
        assertEquals(5, iceCream.getPosition().getY());
        assertTrue(iceCream.getAlive());
        assertFalse(iceCream.isStrawberryActive());
    }

    @Test
    @DisplayName("getType() returns 1")
    void testGetType() {
        assertEquals(1, iceCream.getType());
    }

    @Test
    @DisplayName("changeAlive() toggles alive state")
    void testChangeAlive() {
        assertTrue(iceCream.getAlive());
        iceCream.changeAlive();
        assertFalse(iceCream.getAlive());
        iceCream.changeAlive();
        assertTrue(iceCream.getAlive());
    }

    @Test
    @DisplayName("setStrawberry(...) and isStrawberryActive()")
    void testStrawberry() {
        assertFalse(iceCream.isStrawberryActive());
        iceCream.setStrawberry(true);
        assertTrue(iceCream.isStrawberryActive());
    }

    @Test
    @DisplayName("setLastMovement(...) and getLastMovement()")
    void testSetGetLastMovement() {
        assertEquals(GUI.ACTION.DOWN, iceCream.getLastMovement()); // default
        iceCream.setLastMovement(GUI.ACTION.LEFT);
        assertEquals(GUI.ACTION.LEFT, iceCream.getLastMovement());
    }

    @Test
    @DisplayName("setPosition(...) from parent Element")
    void testSetPosition() {
        Position newPos = new Position(10,10);
        iceCream.setPosition(newPos);
        assertEquals(newPos, iceCream.getPosition());
    }
}
