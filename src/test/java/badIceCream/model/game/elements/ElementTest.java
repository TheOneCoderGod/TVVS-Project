package badIceCream.model.game.elements;

import badIceCream.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Element is abstract, so we create a dummy subclass to test the
 * functionality that Element provides: position management, etc.
 */
class ElementTest {

    private static class DummyElement extends Element {
        public DummyElement(int x, int y) {
            super(x, y);
        }
        @Override
        public int getType() {
            return 999; // arbitrary
        }
    }

    private Element element;

    @BeforeEach
    void setUp() {
        element = new DummyElement(5, 7);
    }

    @Test
    @DisplayName("Constructor sets position correctly")
    void testConstructor() {
        assertEquals(5, element.getPosition().getX());
        assertEquals(7, element.getPosition().getY());
    }

    @Test
    @DisplayName("setPosition(...) updates the position")
    void testSetPosition() {
        Position newPos = new Position(10, 10);
        element.setPosition(newPos);
        assertEquals(newPos, element.getPosition());
    }

    @Test
    @DisplayName("getType() in dummy returns 999")
    void testGetType() {
        assertEquals(999, element.getType());
    }
}
