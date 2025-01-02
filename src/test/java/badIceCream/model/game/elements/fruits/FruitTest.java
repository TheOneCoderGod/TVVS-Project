package badIceCream.model.game.elements.fruits;

import badIceCream.model.game.elements.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Fruit is abstract, so we create a dummy subclass for testing any
 * common functionality from Fruit or from the parent Element.
 */
class FruitTest {

    private static class DummyFruit extends Fruit {
        public DummyFruit(int x, int y) {
            super(x, y);
        }
        @Override
        public int getType() {
            return 999;
        }
    }

    private Fruit dummyFruit;

    @BeforeEach
    void setUp() {
        dummyFruit = new DummyFruit(4, 4);
    }

    @Test
    @DisplayName("Constructor sets X and Y (from Element)")
    void testConstructor() {
        assertEquals(4, dummyFruit.getPosition().getX());
        assertEquals(4, dummyFruit.getPosition().getY());
    }

    @Test
    @DisplayName("getType() in dummy fruit => 999 (just verifying override)")
    void testGetType() {
        assertEquals(999, dummyFruit.getType());
    }
}
